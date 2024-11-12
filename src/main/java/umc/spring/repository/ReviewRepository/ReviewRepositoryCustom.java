package umc.spring.repository.ReviewRepository;

import umc.spring.domain.Store;

import java.util.List;

public interface ReviewRepositoryCustom {
    void insertReview(String title, String content, Float score, Long memberId, Long storeId);
}