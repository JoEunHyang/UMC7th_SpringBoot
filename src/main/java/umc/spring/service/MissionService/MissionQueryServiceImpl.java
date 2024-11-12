package umc.spring.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.dto.MissionDto;
import umc.spring.repository.MissionRepository.MissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService  {

    private final MissionRepository missionRepository;

    @Override
    public List<MissionDto> findMissionsByMemberIdAndStatus(Long memberId, Long cursorValue, MissionStatus status) {
        List<MissionDto> filteredMissions = missionRepository.getMemberMissions(memberId, cursorValue, status);
//        filteredMissions.forEach(mission -> System.out.println("Mission: " + mission));

        return filteredMissions;
    }
}