package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.FoodCategory;
import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.repository.OrderRepository;
import com.pre_capstone_design_24.server.requestDto.OrderRequestDto;
import com.pre_capstone_design_24.server.responseDto.OrderResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final FoodService foodService;

    public List<Order> toOrderList(List<OrderRequestDto> orderRequestDtoList) {
        List<Order> orderList = orderRequestDtoList.stream()
                .map(orderRequestDto -> OrderRequestDto.of(orderRequestDto, foodService))
                .collect(Collectors.toList());
        return orderList;
    }

    public void setOrderHistoryId(List<Order> orders, OrderHistory orderHistory) {
        orders.forEach(order -> order.setOrderHistory(orderHistory));
    }

    public List<OrderResponseDto> toOrderResponseList(List<Order> orders) {
        List<OrderResponseDto> orderResponseDtoList = orders.stream()
                .map(order -> OrderResponseDto.toResponseDto(order))
                .collect(Collectors.toList());
        return orderResponseDtoList;
    }

    public List<Order> getOrdersByOrderHistoryId(Long orderHistoryId) {
        return orderRepository.findAllByOrderHistoryId(orderHistoryId);
    }

    public List<Order> getOrdersByFoodCategory(Long foodCategoryId) {
        return orderRepository.findAllByFoodCategory(foodCategoryId);
    }

    public long getTotalCostOfOrders(List<Order> orderList) {
        long totalCost = 0;
        for(Order order : orderList) {
            totalCost += order.getSumOfCost();
        }
        return totalCost;
    }

    public void saveOrders(List<Order> orders){
        for(Order order : orders) {
            save(order);
        }
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

}
