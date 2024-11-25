package umc.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import umc.spring.domain.enums.MissionStatus;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class MissionDto {
    // Getter 및 Setter
    private Long memberMissionId;
    private Long missionId;
    private MissionStatus missionStatus;  // 미션 상태
    private String storeName;      // 식당 이름
    private Integer missionReward; // 미션 리워드
    private String missionSpec;    // 미션 설명
    private Long cursor;
    private LocalDate deadline;

    public MissionDto() {}

    // 생성자

//    public MissionDto(Long memberMissionId, Long missionId, MissionStatus missionStatus, String storeName, Integer missionReward, String missionSpec, Double storeScore, Long cursor, LocalDate deadline) {
//        this.memberMissionId = memberMissionId;
//        this.missionId = missionId;
//        this.missionStatus = missionStatus;
//        this.storeName = storeName;
//        this.missionReward = missionReward;
//        this.missionSpec = missionSpec;
//        this.cursor = cursor;
//        this.deadline = deadline;
//    }


//    @Override
//    public String toString() {
//        return "MissionDto{" +
//                "memberMissionId=" + memberMissionId +
//                ", missionId=" + missionId +
//                ", missionStatus=" + missionStatus +
//                ", storeName='" + storeName + '\'' +
//                ", missionReward=" + missionReward +
//                ", missionSpec='" + missionSpec + '\'' +
//                ", cursor=" + cursor +
//                ", deadline=" + deadline +
//                '}';
//    }
}