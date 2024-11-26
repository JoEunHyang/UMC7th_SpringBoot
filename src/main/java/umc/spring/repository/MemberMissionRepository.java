package umc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.FoodCategory;
import umc.spring.domain.mapping.MemberMission;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    // MemberMission이 memberId와 missionId로 조회되는 메서드
    boolean  existsByMemberIdAndMissionId(Long memberId, Long missionId);
}
