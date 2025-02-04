package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MemberPreferConverter;
import umc.spring.domain.*;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.domain.mapping.MemberPrefer;
import umc.spring.repository.FoodCategoryRepository;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.ReviewRepository.ReviewRepository;
import umc.spring.web.dto.MemberRequestDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request) {

        //validation
        Member newMember = MemberConverter.toMember(request);
/*        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(category -> {
                    return foodCategoryRepository.findById(category).orElseThrow(() -> new FoodCategoryHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND));
                }).collect(Collectors.toList());*/
        newMember.encodePassword(passwordEncoder.encode(request.getPassword()));

        List<FoodCategory> foodCategoryList = foodCategoryRepository.findAllById(request.getPreferCategory());
        List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);

        memberPreferList.forEach(memberPrefer -> memberPrefer.setMember(newMember));

        return memberRepository.save(newMember);
    }
    public boolean foodCategoryExistsById(Long id) {
        return foodCategoryRepository.existsById(id);
    }

    @Transactional
    @Override
    public Page<Review> getReviewList(Long MemberId, Integer page) {
        Member member = memberRepository.findById(MemberId).get();

        Page<Review> MemberPage = reviewRepository.findAllByMember(member, PageRequest.of(page - 1, 10));
        return MemberPage;
    }

    @Transactional
    @Override
    public Page<MemberMission> getMyMissionList(Long MemberId, MissionStatus missionStatus, Integer page) {

        Page<MemberMission> MyMissionPage = memberMissionRepository.findAllByMemberIdAndStatus(MemberId, missionStatus, PageRequest.of(page , 10));

        return MyMissionPage;
    }
}
