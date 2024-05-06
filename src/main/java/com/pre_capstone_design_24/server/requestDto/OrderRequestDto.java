package com.pre_capstone_design_24.server.requestDto;

import com.pre_capstone_design_24.server.domain.Food;
import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.service.FoodService;
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
public class OrderRequestDto {

    @NotNull
    @Schema(description = "음식 아이디")
    private Long foodId;

    @NotNull
    @Schema(description = "수량")
    private Integer quantity;

    public static Order of(OrderRequestDto orderRequestDto, FoodService foodService) {
        Food food = foodService.getFoodById(orderRequestDto.getFoodId());
        int quantity = orderRequestDto.getQuantity();
        return Order.builder()
                .food(food)
                .quantity(quantity)
                .sumOfCost((long) (food.getPrice() * quantity))
                .build();
    }

}
