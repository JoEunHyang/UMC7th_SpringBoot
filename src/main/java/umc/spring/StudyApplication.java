package umc.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.service.HomeService.HomeQueryService;
import umc.spring.service.MissionService.MissionQueryService;
import umc.spring.service.MypageService.MypageQueryService;
import umc.spring.service.ReviewService.ReviewQueryService;
import umc.spring.service.StoreService.StoreQueryService;

import static umc.spring.domain.QMission.mission;

@SpringBootApplication
@EnableJpaAuditing
public class StudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			StoreQueryService storeService = context.getBean(StoreQueryService.class);

			// 파라미터 값 설정
			String name = "요아정";
			Float score = 4.0f;

			// 쿼리 메서드 호출 및 쿼리 문자열과 파라미터 출력
			System.out.println("Executing findStoresByNameAndScore with parameters:");
			System.out.println("Name: " + name);
			System.out.println("Score: " + score);

			storeService.findStoresByNameAndScore(name, score)
					.forEach(System.out::println);

			MyMission(context);

			review(context);

			homeMission(context);

			myPage(context);
		};
	}



	public void review(ApplicationContext context) {
			ReviewQueryService reviewService = context.getBean(ReviewQueryService.class);

			// 예시 데이터
			Float score = 4.0f;
			Long memberId = 1L;
			Long storeId = 2L;
			String title = "Excellent!";
			String content = "The food was great and the service was amazing!";

			// 리뷰 추가
			reviewService.addReview(title, content, score, memberId, storeId);
	}

	public void MyMission(ApplicationContext context) {
		MissionQueryService missionService = context.getBean(MissionQueryService.class);

		Long memberId = 1L;
		Long cursor = 10L;

		System.out.println("Executing findChallengingMissions with parameters:");
		System.out.println("memberId: " + memberId);
		System.out.println("Cursor: " + cursor);

		missionService.findMissionsByMemberIdAndStatus(1L, 10L, MissionStatus.CHALLENGING)
				.forEach(System.out::println);

		System.out.println("Executing findCompleteMissions with parameters:");
		System.out.println("memberId: " + memberId);
		System.out.println("Cursor: " + cursor);

		missionService.findMissionsByMemberIdAndStatus(1L, 10L, MissionStatus.COMPLETE)
				.forEach(System.out::println);

	}

	public void homeMission(ApplicationContext context) {
		HomeQueryService missionService = context.getBean(HomeQueryService.class);

		String regionName = "서울";
		Long memberId = 2L;
		Long cursor = 0L;

		System.out.println("Executing findChallengingMissions with parameters:");
		System.out.println("memberId: " + memberId);
		System.out.println("Cursor: " + cursor);

		missionService.findMissions(regionName,memberId, cursor)
				.forEach(System.out::println);
	}
	public void myPage(ApplicationContext context) {
		MypageQueryService myPageService = context.getBean(MypageQueryService.class);

		// 예시 데이터
		Long memberId = 2L;

		System.out.println(myPageService.findMember(memberId));
	}
}
