package umc.spring.service.HomeService;


import umc.spring.domain.enums.MissionStatus;
import umc.spring.dto.MissionDto;

import java.util.List;

public interface HomeQueryService {

    List<MissionDto> findMissions(Long memberId, Long cursorValue);
}
