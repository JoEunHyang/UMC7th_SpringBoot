package umc.spring.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.service.ReviewService.ReviewQueryService;
import umc.spring.service.StoreService.StoreQueryService;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;
import umc.spring.web.dto.StoreRequestDTO;
import umc.spring.web.dto.StoreResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Validated // DTO의 Validation 어노테이션 활성화
public class StoreRestController {
    private final StoreQueryService storeQueryService;
    private final ReviewQueryService reviewQueryService;

    @PostMapping("")
    public ApiResponse<StoreResponseDTO.JoinResultDTO> join(@RequestBody @Valid StoreRequestDTO.JoinDto request) {
        Store store = storeQueryService.saveStore(request);
        return ApiResponse.onSuccess(StoreConverter.joinResultDTO(store));
    }

    @PostMapping("/{store_id}/reviews")
    public ApiResponse<ReviewResponseDTO.AddResultDTO> saveReview(@PathVariable("store_id")  Long storeId, @RequestBody @Valid ReviewRequestDTO.AddDto request) {
        Review review = reviewQueryService.saveReview(storeId, request);
        return ApiResponse.onSuccess(ReviewConverter.AddResultDTO(review));
    }
}
