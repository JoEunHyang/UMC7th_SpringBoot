package umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Store;
import umc.spring.repository.ReviewRepository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.service.StoreService.StoreQueryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final StoreRepository storeRepository;

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void addReview(String title, String content, Float score, Long memberId, Long storeId) {
        reviewRepository.insertReview(title, content, score, memberId, storeId);
    }
}