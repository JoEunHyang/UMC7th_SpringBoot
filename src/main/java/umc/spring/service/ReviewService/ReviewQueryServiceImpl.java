package umc.spring.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.ReviewRepository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.service.StoreService.StoreQueryService;
import umc.spring.web.dto.ReviewRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Review saveReview(Long storeId, ReviewRequestDTO.AddDto request) {
        // Store 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // Review 생성 및 저장
        Review review = ReviewConverter.toReview(request, member, store);

        return reviewRepository.save(review);
    }
}