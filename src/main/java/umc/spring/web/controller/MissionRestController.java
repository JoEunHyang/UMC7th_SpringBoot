package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberMissionConverter;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MissionService.MissionQueryService;
import umc.spring.validation.annotation.ExistChallengingMission;
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

    //미션 완료하기
    @PatchMapping("/{mission_id}/complete")
    @Operation(summary = "미션 완료 API",description = "내가 진행중인 미션을 완료하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({//프론트엔드에서 넘겨줘야 할 정보 path나 query
            @Parameter(name = "mission_id", description = "미션 아이디, path variable 입니다!"),
    })
    public ApiResponse<MissionResponseDTO.MissionChallengeResultDTO> completeMission(@PathVariable("mission_id") @ExistChallengingMission Long missionId) {
        MemberMission memberMission = missionQueryService.completeMission(missionId);
        return ApiResponse.onSuccess(MemberMissionConverter.MissionChallengeResultDTO(memberMission));
    }

}
