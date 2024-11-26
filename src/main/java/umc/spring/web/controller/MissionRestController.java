package umc.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MissionService.MissionQueryService;
import umc.spring.validation.annotation.ExistMemberMission;
import umc.spring.web.dto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
@Validated // DTO의 Validation 어노테이션 활성화
public class MissionRestController {
    private final MissionQueryService missionQueryService;

    @PostMapping("/{mission_id}/challenge")
    public ApiResponse<MissionResponseDTO.MissionChallengeResultDTO> challengeMission(@PathVariable("mission_id") @ExistMemberMission Long missionId) {
        MemberMission memberMission = missionQueryService.challengeMission(missionId);
        return ApiResponse.onSuccess(MemberMissionConverter.MissionChallengeResultDTO(memberMission));
    }

}
