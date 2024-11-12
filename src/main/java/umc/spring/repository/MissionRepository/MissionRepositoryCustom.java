package umc.spring.repository.MissionRepository;

import umc.spring.domain.enums.MissionStatus;
import umc.spring.dto.MissionDto;

import java.util.List;

public interface MissionRepositoryCustom {
    List<MissionDto> getMemberMissions(Long memberId, Long cursorValue , MissionStatus status);
    List<MissionDto> getMissions(Long memberId, Long cursorValue);

}
