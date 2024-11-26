package umc.spring.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.service.MissionService.MissionQueryService;
import umc.spring.service.ReviewService.ReviewQueryService;
import umc.spring.service.StoreService.StoreQueryService;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.web.dto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Validated // DTO의 Validation 어노테이션 활성화
public class StoreRestController {
    private final StoreQueryService storeQueryService;
    private final ReviewQueryService reviewQueryService;
    private final MissionQueryService missionQueryService;

    @PostMapping("")
    public ApiResponse<StoreResponseDTO.StoreAddResultDTO> join(@RequestBody @Valid StoreRequestDTO.StoreAddDto request) {
        Store store = storeQueryService.saveStore(request);
        return ApiResponse.onSuccess(StoreConverter.joinResultDTO(store));
    }

    //리뷰 추가
    @PostMapping("/{store_id}/reviews")
    public ApiResponse<ReviewResponseDTO.AddResultDTO> saveReview(@PathVariable("store_id") @ExistStore Long storeId, @RequestBody @Valid ReviewRequestDTO.AddDto request) {
        Review review = reviewQueryService.saveReview(storeId, request);
        return ApiResponse.onSuccess(ReviewConverter.AddResultDTO(review));
    }

    //미션 추가
    @PostMapping("/{store_id}/missions")
    public ApiResponse<MissionResponseDTO.MissionAddResultDTO> addMission(@PathVariable("store_id") @ExistStore Long storeId, @RequestBody @Valid MissionRequestDTO.MissionAddDto request) {
        Mission mission = missionQueryService.addMission(storeId, request);
        return ApiResponse.onSuccess(MissionConverter.MissionAddResultDTO(mission));
    }
}
