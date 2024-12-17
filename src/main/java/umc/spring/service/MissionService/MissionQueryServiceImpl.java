package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.dto.MissionDto;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.MissionRepository.MissionRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.MissionRequestDTO;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<MissionDto> findMissionsByMemberIdAndStatus(Long memberId, Long cursorValue, MissionStatus status) {
        List<MissionDto> filteredMissions = missionRepository.getMemberMissions(memberId, cursorValue, status);
//        filteredMissions.forEach(mission -> System.out.println("Mission: " + mission));

        return filteredMissions;
    }

    @Transactional
    @Override
    public Mission addMission(Long storeId, MissionRequestDTO.MissionAddDto request) {
        // Store 확인
        Store store = storeRepository.findById(storeId).orElseThrow();

        Mission mission = MissionConverter.toMission(request, store);

        return missionRepository.save(mission);
    }

    //미션 도전하기
    @Transactional
    @Override
    public MemberMission challengeMission(Long missionId) {
        // Mission 확인
        Mission mission = missionRepository.findById(missionId).get();
//                .orElseThrow(() -> new RuntimeException("Mission not found"));  // missionId로 Mission 존재 확인

        Member member = memberRepository.findById(9L).get();              //임의
//                .orElseThrow(() -> new RuntimeException("Member not found"));  // memberId로 Member 존재 확인

        // 새로운 MemberMission 생성
        MemberMission newMemberMission = new MemberMission(MissionStatus.CHALLENGING, member, mission); // toMemberMission 변환 메서드 사용

        // MemberMission 저장
        memberMissionRepository.save(newMemberMission);

        return newMemberMission;
    }
    public boolean memberMissionExistsById(Long memberId, Long missionId) {
        return memberMissionRepository.existsByMemberIdAndMissionId(memberId, missionId);
    }

    //미션 완료하기
    @Transactional
    @Override
    public MemberMission completeMission(Long memberMissionId) {
        // 기존 MemberMission 확인
        MemberMission existingMemberMission = memberMissionRepository.findById(memberMissionId).get();

        // 상태 업데이트
        existingMemberMission.setStatus(MissionStatus.COMPLETE);

        // 저장
        memberMissionRepository.save(existingMemberMission);

        return existingMemberMission;
    }
    public boolean memberMissionExistsByIdAndStatus(Long memberMissionId, MissionStatus status) {
        return memberMissionRepository.existsByIdAndStatus(memberMissionId, status);
    }
}