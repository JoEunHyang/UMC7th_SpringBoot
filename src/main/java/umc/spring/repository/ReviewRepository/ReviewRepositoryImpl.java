package umc.spring.repository.ReviewRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Member;
import umc.spring.domain.QStore;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.StoreRepository.StoreRepositoryCustom;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom  {
    private final EntityManager entityManager;

    @Transactional
    public void insertReview(String title, String content, Float score, Long memberId, Long storeId) {
        // Member와 Store 객체를 미리 로드
        Member member = entityManager.getReference(Member.class, memberId);
        Store store = entityManager.getReference(Store.class, storeId);

        // Review 객체 생성 및 저장
        Review review = Review.builder()
                .title(title)
                .content(content)
                .score(score)
                .member(member)
                .store(store)
                .build();

        entityManager.persist(review);
    }
}