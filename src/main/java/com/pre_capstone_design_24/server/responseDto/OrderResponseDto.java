package com.pre_capstone_design_24.server.responseDto;

import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.service.FoodService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderResponseDto {

    private Long orderHistoryId;

    private String foodName;

    private Integer quantity;

    private Integer sumOfCost;

    public static OrderResponseDto toResponseDto(Order order) {
        return OrderResponseDto.builder()
                .orderHistoryId(order.getOrderHistory().getId())
                .foodName(order.getFood().getName())
                .quantity(order.getQuantity())
                .sumOfCost(order.getSumOfCost())
                .build();
    }

}
