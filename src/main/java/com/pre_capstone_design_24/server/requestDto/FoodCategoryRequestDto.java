package com.pre_capstone_design_24.server.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCategoryRequestDto {

    @NotNull
    @Schema(description = "음식 카테고리 이름", example = "김밥")
    private String name;

}
