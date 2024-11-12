package umc.spring.service.ReviewService;


import umc.spring.domain.Store;

import java.util.List;
import java.util.Optional;

public interface ReviewQueryService {
    void addReview(String title, String content, Float score, Long memberId, Long storeId);
}
