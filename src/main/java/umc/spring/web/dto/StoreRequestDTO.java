package umc.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotation.ExistRegions;


public class StoreRequestDTO {
    @Getter
    public static class StoreAddDto{
        @NotBlank
        String name;

        String address;

        Float score;

        @ExistRegions(message = "Invalid Region ID provided.")
        Long regionId;
    }
}
