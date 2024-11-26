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
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));

        Mission mission = MissionConverter.toMission(request, store);

        return missionRepository.save(mission);
    }

    //미션 도전하기
    @Transactional
    @Override
    public MemberMission challengeMission(Long missionId) {
        // Mission 확인
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));  // missionId로 Mission 존재 확인

        Member member = memberRepository.findById(9L)                //임의
                .orElseThrow(() -> new RuntimeException("Mission not found"));  // missionId로 Mission 존재 확인

        // 이미 해당 memberId와 missionId에 해당하는 MemberMission이 있는지 확인
        MemberMission existingMemberMission = memberMissionRepository.findByMemberIdAndMissionId(9L, missionId);
        if (existingMemberMission != null) {
            // 이미 존재하는 경우 예외 처리
            throw new RuntimeException("Mission already exists for this member");  // 예외 메시지
        }
        // 새로운 MemberMission 생성
        MemberMission newMemberMission = new MemberMission(MissionStatus.CHALLENGING, member, mission); // toMemberMission 변환 메서드 사용

        // MemberMission 저장
        memberMissionRepository.save(newMemberMission);

        return newMemberMission;
    }
}