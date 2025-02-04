package umc.spring.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.spring.apiPayload.code.BaseErrorCode;
import umc.spring.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 멤버 관려 에러
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

    // 예시,,,
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

    //FoodCategory
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY4001", "음식 종류가 없습니다."),


    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION4001", "지역이 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE4001", "해당 Store가 존재하지 않습니다"),
    MEMBER_MISSION_ALREADY_EXIST(HttpStatus.NOT_FOUND, "MEMBERMISSION4001", "이미 도전중이거나 완료함"),
    CHALLENGING_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBERMISSION4002", "도전하지 않은 미션이거나 이미 완료한 미션입니다."),
    PAGE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "PAGE4001", "page는 1이상이어야 합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}