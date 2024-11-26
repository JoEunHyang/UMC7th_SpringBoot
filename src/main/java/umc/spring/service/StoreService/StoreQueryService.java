package umc.spring.service.StoreService;


import umc.spring.domain.Store;
import umc.spring.web.dto.StoreRequestDTO;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {

    Optional<Store> findStore(Long id);
    List<Store> findStoresByNameAndScore(String name, Float score);

    Store saveStore(StoreRequestDTO.StoreAddDto request);
}
