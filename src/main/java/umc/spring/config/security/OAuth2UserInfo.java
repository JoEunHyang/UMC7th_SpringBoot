package umc.spring.config.security;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import umc.spring.domain.Member;
import umc.spring.domain.enums.SocialType;

import java.util.Map;

@Builder
@Getter
@ToString
public class OAuth2UserInfo {
//    private String id;
//    private String password;
    private String email;
    private String nickname;
    private SocialType provider;

    public static OAuth2UserInfo of(String provider, Map<String, Object> attributes) {
        switch (provider) {
            case "google":
                return ofGoogle(attributes);
            case "kakao":
                return ofKakao(attributes);
   /*         case "naver":
                return ofNaver(attributes);*/
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .provider(SocialType.GOOGLE)
//                .id("google_" + (String) attributes.get("sub"))
//                .password((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .provider(SocialType.KAKAO)
//                .id("kakao_" + attributes.get("id").toString())
//                .password(attributes.get("id").toString())
                .nickname((String) ((Map) attributes.get("properties")).get("nickname"))
                .email((String) ((Map) attributes.get("properties")).get("nickname")+ "@kakao.com")
                .build();
    }

/*    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .provider("naver")
                .id("naver_" + (String) ((Map) attributes.get("response")).get("id"))
                .password((String) ((Map) attributes.get("response")).get("id"))
                .nickname((String) ((Map) attributes.get("response")).get("name"))
                .build();
    }*/

/*    public Member toEntity() {
        return Member.builder()
                .id(id)
                .password(password)
                .provider(provider)
                .nickname(nickname)
                .email(email)
                .userRole(UserRole.MEMBER)
                .build();
    }*/

}
