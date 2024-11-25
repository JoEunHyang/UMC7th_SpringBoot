package umc.spring.converter;

import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.web.dto.StoreRequestDTO;
import umc.spring.web.dto.StoreResponseDTO;

import java.time.LocalDateTime;

public class StoreConverter {

    public static StoreResponseDTO.JoinResultDTO joinResultDTO(Store store) {
        return StoreResponseDTO.JoinResultDTO.builder()
                .storeId(store.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Store toStore(StoreRequestDTO.JoinDto request, Region region){

        return Store.builder()
                .address(request.getAddress())
                .name(request.getName())
                .score(request.getScore())
                .region(region)// region은 ID로 받아서 Service 계층에서 조회한 객체를 주입
                .build();
    }
}
