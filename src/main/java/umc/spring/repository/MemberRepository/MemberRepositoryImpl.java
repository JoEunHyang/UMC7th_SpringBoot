package umc.spring.repository.MemberRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Member;
import umc.spring.domain.QMember;
import umc.spring.domain.QStore;
import umc.spring.domain.Store;
import umc.spring.repository.StoreRepository.StoreRepositoryCustom;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {//impl repository 사용하는 클래스가 하나일 때만 붙여줌
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember member = QMember.member;

    @Override
    public Member dynamicQueryWithBooleanBuilder(Long memberId) {
        BooleanBuilder predicate = new BooleanBuilder();


        return jpaQueryFactory.select(member)  // Member 객체 자체를 반환
                .from(member)
                .where(member.id.eq(memberId))  // id로 필터링
                .fetchOne();  // 한 줄만 반환
    }
}