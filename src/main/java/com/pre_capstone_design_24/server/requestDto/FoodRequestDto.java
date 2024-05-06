package com.pre_capstone_design_24.server.requestDto;

import com.pre_capstone_design_24.server.domain.FoodCategory;
import com.pre_capstone_design_24.server.domain.FoodStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestDto {

    @Schema(description = "음식 카테고리", example = "1")
    private Long foodCategoryId;

    @NotNull
    @Schema(description = "음식 이름", example = "참치김밥")
    private String name;

    @NotNull
    @Schema(description = "음식 가격", example = "4500")
    private int price;

    @NotNull
    @Schema(description = "사진 url", example = "C:/")
    private String pictureURL;

    @NotNull
    @Schema(description = "음식 상태", example = "SALE")
    private FoodStatus status;

}