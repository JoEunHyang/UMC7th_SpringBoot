package umc.spring.service.StoreService;


import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.StoreRequestDTO;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {

    Optional<Store> findStore(Long id);
    List<Store> findStoresByNameAndScore(String name, Float score);

    Store saveStore(StoreRequestDTO.StoreAddDto request);

    Review saveReview(Long storeId, ReviewRequestDTO.AddDto request );

    Page<Review> getReviewList(Long StoreId, Integer page);

    Page<Mission> getMissionList(Long StoreId, Integer page);
}
