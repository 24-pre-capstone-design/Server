package com.pre_capstone_design_24.server.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pre_capstone_design_24.server.domain.Food;
import com.pre_capstone_design_24.server.domain.FoodCategory;
import com.pre_capstone_design_24.server.domain.FoodStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FoodResponseDto {

    Long id;

    FoodCategory foodCategory;

    String name;

    int price;

    String pictureURL;

    FoodStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    public static FoodResponseDto of(Food food) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .foodCategory(food.getFoodCategory())
                .name(food.getName())
                .price(food.getPrice())
                .pictureURL(food.getPictureURL())
                .status(food.getStatus())
                .createdAt(food.getCreatedAt())
                .build();
    }

}