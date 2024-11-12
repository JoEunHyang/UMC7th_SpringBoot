package umc.spring.repository.MissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import umc.spring.domain.QMission;
import umc.spring.domain.QRegion;
import umc.spring.domain.QStore;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.QMemberMission;
import umc.spring.dto.MissionDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom  {
    private final JPAQueryFactory jpaQueryFactory;
    private final QStore store = QStore.store;
    private final QMemberMission memberMission = QMemberMission.memberMission;
    private final QMission mission = QMission.mission;
    private final QRegion region = QRegion.region;

    @Override
    public List<MissionDto> getMemberMissions(Long memberId, Long cursorValue, MissionStatus status) {
        BooleanBuilder predicate = new BooleanBuilder();

        // memberId 필터링
        if (memberId != null) {
            predicate.and(memberMission.member.id.eq(memberId));
        }

        // 상태 필터링
        predicate.and(memberMission.status.eq(status));

        // 커서 값 필터링
        if (cursorValue != null) {
            predicate.and(memberMission.mission.id.lt(cursorValue));
        }

        return jpaQueryFactory
                .select(Projections.fields(
                        MissionDto.class,
                        memberMission.id.as("memberMissionId"),
                        mission.id.as("missionId"),
                        memberMission.status.as("missionStatus"),
                        store.name.as("storeName"),
                        mission.reward.as("missionReward"),
                        mission.missionSpec.as("missionSpec")
                ))
                .from(memberMission)
                .join(memberMission.mission, mission)
                .join(mission.store, store)
                .where(predicate)
                .orderBy(memberMission.mission.id.desc()) // 최근 생성 순 정렬
                .limit(15)  // 최대 15개 제한
                .fetch();
    }

    @Override
    public List<MissionDto> getMissions(Long memberId, Long cursorValue) {
        return List.of();
    }


}