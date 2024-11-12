package umc.spring.service.MypageService;


import umc.spring.domain.Member;
import umc.spring.domain.Store;

import java.util.List;
import java.util.Optional;

public interface MypageQueryService {

    Optional<Member> findMember(Long id);
}
