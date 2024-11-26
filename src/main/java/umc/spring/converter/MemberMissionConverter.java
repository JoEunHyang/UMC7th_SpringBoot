package umc.spring.converter;

import umc.spring.domain.FoodCategory;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.domain.mapping.MemberPrefer;
import umc.spring.web.dto.MissionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MemberMissionConverter {

    public static MissionResponseDTO.MissionChallengeResultDTO MissionChallengeResultDTO(MemberMission memberMission) {
        return MissionResponseDTO.MissionChallengeResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .status(memberMission.getStatus())
                .createdAt(LocalDateTime.now()) // 혹은 review.getCreatedAt() (BaseEntity 활용 시)
                .build();
    }
}
