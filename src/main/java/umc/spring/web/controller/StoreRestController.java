package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.service.MissionService.MissionQueryService;
import umc.spring.service.StoreService.StoreQueryService;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.web.dto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Validated // DTO의 Validation 어노테이션 활성화
public class StoreRestController {
    private final StoreQueryService storeQueryService;
    private final MissionQueryService missionQueryService;

    @PostMapping("")
    public ApiResponse<StoreResponseDTO.StoreAddResultDTO> join(@RequestBody @Valid StoreRequestDTO.StoreAddDto request) {
        Store store = storeQueryService.saveStore(request);
        return ApiResponse.onSuccess(StoreConverter.joinResultDTO(store));
    }

    //리뷰 추가
    @PostMapping("/{store_id}/reviews")
    public ApiResponse<StoreResponseDTO.ReviewAddResultDTO> saveReview(@PathVariable("store_id") @ExistStore Long storeId, @RequestBody @Valid ReviewRequestDTO.AddDto request) {
        Review review = storeQueryService.saveReview(storeId, request);
        return ApiResponse.onSuccess(StoreConverter.ReviewAddResultDTO(review));
    }
    //리뷰 목록 조회
    @GetMapping("/{store_id}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API",description = "특정 가게의 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "acess 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "acess 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({//프론트엔드에서 넘겨줘야 할 정보 path나 query
            @Parameter(name = "store_id", description = "가게의 아이디, path variable 입니다!")
    })
    public ApiResponse<StoreResponseDTO.ReviewPreViewListDTO> getReviewList(@ExistStore @PathVariable(name = "store_id") Long storeId, @CheckPage @RequestParam(name = "page") Integer page){
        Page<Review> reviewList = storeQueryService.getReviewList(storeId,page);
        return ApiResponse.onSuccess(StoreConverter.reviewPreViewListDTO(reviewList));
    }

    //미션 추가
    @PostMapping("/{store_id}/missions")
    public ApiResponse<MissionResponseDTO.MissionAddResultDTO> addMission(@PathVariable("store_id") @ExistStore Long storeId, @RequestBody @Valid MissionRequestDTO.MissionAddDto request) {
        Mission mission = missionQueryService.addMission(storeId, request);
        return ApiResponse.onSuccess(MissionConverter.MissionAddResultDTO(mission));
    }
}
