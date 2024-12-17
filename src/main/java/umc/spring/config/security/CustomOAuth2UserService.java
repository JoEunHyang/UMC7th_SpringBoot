package umc.spring.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import umc.spring.domain.Member;
import umc.spring.domain.enums.Gender;
import umc.spring.domain.enums.Role;
import umc.spring.domain.enums.SocialType;
import umc.spring.repository.MemberRepository.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//카카오 로그인 후 받은 사용자 정보를 처리하고 데이터베이스에 저장하는 역할
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        // 2. provider : kakao, naver, google
        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("provider : {}", provider);

        // 3. 필요한 정보를 provider에 따라 다르게 mapping
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(provider, oAuth2User.getAttributes());
        log.info("oAuth2UserInfo : {}", oAuth2UserInfo.toString());

//        String nickname = (String) properties.get("nickname");
//        String email = nickname + "@kakao.com"; // 임시 이메일 생성

        // 사용자 정보 저장 또는 업데이트
        Member member = saveOrUpdateUser(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getNickname(),oAuth2UserInfo.getProvider());

        // 이메일을 Principal로 사용하기 위해 attributes 수정
        Map<String, Object> modifiedAttributes = new HashMap<>(attributes);
        modifiedAttributes.put("email", oAuth2UserInfo.getEmail());

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                modifiedAttributes,
                "email"  // email Principal로 설정
        );
    }

    private Member saveOrUpdateUser(String email, String nickname, SocialType provider) {
        Member member = memberRepository.findByEmail(email)
                .orElse(Member.builder()
                        .email(email)
                        .name(nickname)
                        .password(passwordEncoder.encode("OAUTH_USER_" + UUID.randomUUID()))
                        .gender(Gender.NONE)  // 기본값 설정
                        .address("소셜로그인")  // 기본값 설정
                        .specAddress("소셜로그인")  // 기본값 설정
                        .role(Role.USER)
                        .socialType(provider)
                        .build());

        return memberRepository.save(member);
    }
}