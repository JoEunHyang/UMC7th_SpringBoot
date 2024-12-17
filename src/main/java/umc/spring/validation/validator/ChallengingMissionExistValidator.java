package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.service.MissionService.MissionQueryServiceImpl;
import umc.spring.validation.annotation.ExistChallengingMission;
import umc.spring.validation.annotation.ExistMemberMission;


@Component
@RequiredArgsConstructor
public class ChallengingMissionExistValidator implements ConstraintValidator<ExistChallengingMission, Long> {

//    private final StoreQueryServiceImpl storeQueryService;
    private final MissionQueryServiceImpl missionQueryService;

    @Override
    public void initialize(ExistChallengingMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        //임시로 memberId 넣어둠. 하드코딩
        boolean isValid = missionQueryService.memberMissionExistsByIdAndStatus(id, MissionStatus.CHALLENGING);//(value->memberCommandService.existsById(value));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.CHALLENGING_MISSION_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isValid;

    }
}