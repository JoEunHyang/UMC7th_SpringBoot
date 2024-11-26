package umc.spring.converter;

import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;


import java.time.LocalDateTime;

public class ReviewConverter {

    // Review 엔티티를 AddResultDTO로 변환
    public static ReviewResponseDTO.AddResultDTO AddResultDTO(Review review) {
        return ReviewResponseDTO.AddResultDTO.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .createdAt(LocalDateTime.now()) // 혹은 review.getCreatedAt() (BaseEntity 활용 시)
                .build();
    }

    // ReviewRequestDTO와 Member, Store를 받아 Review 엔티티 생성
    public static Review toReview(ReviewRequestDTO.AddDto request, Member member, Store store) {
        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .score(request.getScore())
                .member(member) // Member 객체는 Service 계층에서 조회 후 주입
                .store(store)   // Store 객체는 Service 계층에서 조회 후 주입
                .build();
    }
}
