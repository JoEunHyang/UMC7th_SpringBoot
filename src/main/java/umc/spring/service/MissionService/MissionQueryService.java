package umc.spring.service.MissionService;


import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.dto.MissionDto;
import umc.spring.web.dto.MissionRequestDTO;
import umc.spring.web.dto.ReviewRequestDTO;

import java.util.List;

public interface MissionQueryService {

    List<MissionDto> findMissionsByMemberIdAndStatus(Long memberId, Long cursorValue, MissionStatus status);

    Mission addMission(Long storeId, MissionRequestDTO.MissionAddDto request);

    MemberMission challengeMission(Long missionId);

    MemberMission completeMission(Long memberMissionId);

}
