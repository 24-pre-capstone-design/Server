package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.domain.OrderHistoryStatus;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.OrderHistoryRepository;
import com.pre_capstone_design_24.server.requestDto.OrderHistoryRequestDto;
import com.pre_capstone_design_24.server.requestDto.OrderRequestDto;
import com.pre_capstone_design_24.server.responseDto.OrderHistoryResponseDto;
import com.pre_capstone_design_24.server.responseDto.OrderResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderService orderService;

    private final PaymentService paymentService;

    public void createOrderHistory(OrderHistoryRequestDto orderHistoryRequestDto) {
        Long paymentId = orderHistoryRequestDto.getPaymentId();
        List<OrderRequestDto> orderRequestDtoList = orderHistoryRequestDto.getOrderRequestDtoList();
        List<Order> orders = orderService.toOrderList(orderRequestDtoList);
        orderService.saveOrders(orders);

        OrderHistory orderHistory = OrderHistory.builder()
                .payment(paymentService.findPaymentById(paymentId))
                .orderList(orders)
                .orderHistoryStatus(OrderHistoryStatus.NEW)
                .build();

        orderService.setOrderHistoryId(orders, orderHistory);

        save(orderHistory);
    }

    public List<OrderHistoryResponseDto> getOrderHistoryByPaymentId(Long paymentId) {
        if (!paymentService.isPaymentExist(paymentId))
            throw new GeneralException(Status.PAYMENT_NOT_FOUND);

        List<OrderHistory> orderHistoryList = findOrderHistoryByPaymentId(paymentId);

        List<OrderHistoryResponseDto> orderHistoryResponseDtoList = new ArrayList<>();

        for (OrderHistory history : orderHistoryList) {
            List<OrderResponseDto> orderResponseDtoList = orderService.toOrderResponseList(history.getOrderList());
            OrderHistoryResponseDto orderHistoryResponseDto = OrderHistoryResponseDto.builder()
                    .paymentId(paymentId)
                    .orderResponseDtoList(orderResponseDtoList)
                    .orderHistoryStatus(history.getOrderHistoryStatus())
                    .orderedAt(history.getCreatedAt())
                    .build();
            orderHistoryResponseDtoList.add(orderHistoryResponseDto);
        }

        return orderHistoryResponseDtoList;
    }

    public List<OrderHistory> findOrderHistoryByPaymentId(Long paymentId) {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAllByPaymentId(paymentId);
        return orderHistoryList;
    }

    public void save(OrderHistory orderHistory) {
        orderHistoryRepository.save(orderHistory);
    }

}
