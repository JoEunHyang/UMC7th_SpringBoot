package umc.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    // MemberMission이 memberId와 missionId로 조회되는 메서드
    boolean  existsByMemberIdAndMissionId(Long memberId, Long missionId);
    boolean existsByIdAndStatus(Long memberMissionId, MissionStatus status);

    Page<MemberMission> findAllByMemberIdAndStatus
            (Long memberId, MissionStatus status, PageRequest pageRequest);

    Optional<MemberMission> findByMemberIdAndMissionId(Long memberId, Long missionId);
}
