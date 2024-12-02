package umc.spring.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import umc.spring.validation.validator.ChallengingMissionExistValidator;
import umc.spring.validation.validator.MemberMissionExistValidator;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ChallengingMissionExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistChallengingMission {

    String message() default "이미 도전 중이거나 완료한 미션입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}