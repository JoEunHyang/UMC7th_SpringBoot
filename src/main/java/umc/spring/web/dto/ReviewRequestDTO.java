package umc.spring.web.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReviewRequestDTO {
    @Getter
    public static class AddDto{
        @NotBlank
        String title;

        String content;

        @NotNull
        @DecimalMin(value = "0.0", message = "평점은 0.0 이상이어야 합니다.")
        @DecimalMax(value = "5.0", message = "평점은 5.0 이하이어야 합니다.")
        Float score;

        @NotNull
        Long memberId;
    }
}
