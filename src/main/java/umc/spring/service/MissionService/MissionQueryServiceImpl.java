package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Store;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.dto.MissionDto;
import umc.spring.repository.MissionRepository.MissionRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.MissionRequestDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService  {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

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
}