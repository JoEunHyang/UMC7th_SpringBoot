package umc.spring.repository.MemberRepository;

import umc.spring.domain.Member;
import umc.spring.domain.Store;

import java.util.List;

public interface MemberRepositoryCustom {
    Member dynamicQueryWithBooleanBuilder(Long memberId);
}