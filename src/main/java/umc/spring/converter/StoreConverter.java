package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Region;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.StoreRequestDTO;
import umc.spring.web.dto.StoreResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StoreConverter {

    public static StoreResponseDTO.StoreAddResultDTO joinResultDTO(Store store) {
        return StoreResponseDTO.StoreAddResultDTO.builder()
                .storeId(store.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Store toStore(StoreRequestDTO.StoreAddDto request, Region region){

        return Store.builder()
                .address(request.getAddress())
                .name(request.getName())
                .score(request.getScore())
                .region(region)// region은 ID로 받아서 Service 계층에서 조회한 객체를 주입
                .build();
    }


    //리뷰 추가
    // Review 엔티티를 AddResultDTO로 변환
    public static StoreResponseDTO.ReviewAddResultDTO ReviewAddResultDTO(Review review) {
        return StoreResponseDTO.ReviewAddResultDTO.builder()
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

    //응답을 위한 Entity to DTO
    //스토어 리뷰 목록 보기
    public static StoreResponseDTO.ReviewPreViewDTO reviewPreViewDTO(Review review){
        return StoreResponseDTO.ReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .score(review.getScore())
                .createdAt(review.getCreatedAt().toLocalDate())
                .body(review.getContent())
                .build();
    }
    public static StoreResponseDTO.ReviewPreViewListDTO reviewPreViewListDTO(Page<Review> reviewList){

        List<StoreResponseDTO.ReviewPreViewDTO> reviewPreViewDTOList = reviewList.stream()
                .map(StoreConverter::reviewPreViewDTO).collect(Collectors.toList());

        return StoreResponseDTO.ReviewPreViewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst(reviewList.isFirst())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }


}
