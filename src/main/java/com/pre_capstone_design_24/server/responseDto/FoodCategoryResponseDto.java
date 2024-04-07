package com.pre_capstone_design_24.server.responseDto;

import com.pre_capstone_design_24.server.domain.Food;
import com.pre_capstone_design_24.server.domain.FoodCategory;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodCategoryResponseDto {

    Long id;

    String name;

    public static FoodCategoryResponseDto of(FoodCategory foodCategory) {
        return FoodCategoryResponseDto.builder()
                .id(foodCategory.getId())
                .name(foodCategory.getName())
                .build();
    }

}
