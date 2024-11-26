package umc.spring.service.StoreService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Region;
import umc.spring.domain.Store;
import umc.spring.repository.RegionRepository;
import umc.spring.repository.StoreRepository.StoreRepository;
import umc.spring.web.dto.StoreRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryServiceImpl implements StoreQueryService{

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;

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

}