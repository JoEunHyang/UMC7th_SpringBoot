package umc.spring.service.ReviewService;


import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.ReviewRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewQueryService {
//    void addReview(String title, String content, Float score, Long memberId, Long storeId);
    Review saveReview(Long storeId, ReviewRequestDTO.AddDto request );
}
