package umc.spring.service.StoreService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Region;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository.MemberRepository;
import umc.spring.repository.RegionRepository;
import umc.spring.repository.ReviewRepository.ReviewRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.StoreRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryServiceImpl implements StoreQueryService{

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Optional<Store> findStore(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public List<Store> findStoresByNameAndScore(String name, Float score) {
        List<Store> filteredStores = storeRepository.dynamicQueryWithBooleanBuilder(name, score);

        filteredStores.forEach(store -> System.out.println("Store: " + store));

        return filteredStores;
    }

    //가게 등록
    @Transactional
    @Override
    public Store saveStore(StoreRequestDTO.StoreAddDto request) {
        // Region 유효성 검사가 이미 완료된 상태
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Region not found for ID: "));

        Store newStore = StoreConverter.toStore(request, region);

        return storeRepository.save(newStore);
    }
    //지역 확인
    public boolean regionExistsById(Long id) {
        return regionRepository.existsById(id);
    }
    public boolean storeExistsById(Long id) {
        return storeRepository.existsById(id);
    }

    //리뷰 등록
    @Transactional
    @Override
    public Review saveReview(Long storeId, ReviewRequestDTO.AddDto request) {
        // Store 확인
        Store store = storeRepository.findById(storeId).orElseThrow();// 컨트롤러에서 예외 처리함.

        Member member = memberRepository.findById(9L)                //임의
                .orElseThrow();

//        Member member = memberRepository.findById(request.getMemberId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // Review 생성 및 저장
        Review review = StoreConverter.toReview(request, member, store);

        return reviewRepository.save(review);
    }
    @Override
    public Page<Review> getReviewList(Long StoreId, Integer page) {
        Store store = storeRepository.findById(StoreId).get();

        Page<Review> StorePage = reviewRepository.findAllByStore(store, PageRequest.of(page, 10));
        return StorePage;
    }
}