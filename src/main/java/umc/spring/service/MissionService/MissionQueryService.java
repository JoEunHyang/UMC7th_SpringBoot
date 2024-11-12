package umc.spring.service.MissionService;


import umc.spring.domain.enums.MissionStatus;
import umc.spring.dto.MissionDto;

import java.util.List;

public interface MissionQueryService {

    List<MissionDto> findMissionsByMemberIdAndStatus(Long memberId, Long cursorValue, MissionStatus status);
}
