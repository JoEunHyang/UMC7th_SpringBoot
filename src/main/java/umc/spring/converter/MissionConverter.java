package umc.spring.converter;

import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.MissionRequestDTO;
import umc.spring.web.dto.MissionResponseDTO;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;

public class MissionConverter {

    public static MissionResponseDTO.MissionAddResultDTO MissionAddResultDTO(Mission mission) {
        return MissionResponseDTO.MissionAddResultDTO.builder()
                .missionId(mission.getId())
                .storeId(mission.getStore().getId())
                .createdAt(LocalDateTime.now()) // 혹은 review.getCreatedAt() (BaseEntity 활용 시)
                .build();
    }

    public static Mission toMission(MissionRequestDTO.MissionAddDto request, Store store) {
        return Mission.builder()
                .reward(request.getReward())
                .missionSpec(request.getMissionSpec())
                .deadline(request.getDeadline())
                .store(store)   // Store 객체는 Service 계층에서 조회 후 주입
                .build();
    }
}
