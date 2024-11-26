package umc.spring.service.ReviewService;


import umc.spring.domain.Review;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.web.dto.ReviewRequestDTO;

public interface ReviewQueryService {
//    void addReview(String title, String content, Float score, Long memberId, Long storeId);
    Review saveReview(Long storeId, ReviewRequestDTO.AddDto request );
}
