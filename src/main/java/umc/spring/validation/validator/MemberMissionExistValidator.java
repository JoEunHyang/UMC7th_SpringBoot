package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.service.MissionService.MissionQueryServiceImpl;
import umc.spring.service.StoreService.StoreQueryServiceImpl;
import umc.spring.validation.annotation.ExistMemberMission;
import umc.spring.validation.annotation.ExistStore;


@Component
@RequiredArgsConstructor
public class MemberMissionExistValidator implements ConstraintValidator<ExistMemberMission, Long> {

//    private final StoreQueryServiceImpl storeQueryService;
    private final MissionQueryServiceImpl missionQueryService;

    @Override
    public void initialize(ExistMemberMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        //임시로 memberId 넣어둠. 하드코딩
        boolean isValid = missionQueryService.memberMissionExistsById(9L, id);//(value->memberCommandService.existsById(value));

        if (isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_MISSION_ALREADY_EXIST.toString()).addConstraintViolation();
        }

        return !isValid;

    }
}