package umc.spring.service.HomeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.dto.MissionDto;
import umc.spring.repository.MissionRepository.MissionRepository;
import umc.spring.service.MissionService.MissionQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeQueryServiceImpl implements HomeQueryService {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionDto> findMissions(Long memberId, Long cursorValue) {
        List<MissionDto> filteredMissions = missionRepository.getMissions(memberId, cursorValue);
//        filteredMissions.forEach(mission -> System.out.println("Mission: " + mission));

        return filteredMissions;
    }
}