package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.repository.OrderHistoryRepository;
import com.pre_capstone_design_24.server.requestDto.OrderHistoryRequestDto;
import com.pre_capstone_design_24.server.requestDto.OrderRequestDto;
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
                .build();

        orderService.setOrderHistoryId(orders, orderHistory);

        save(orderHistory);
    }

    public void save(OrderHistory orderHistory) {
        orderHistoryRepository.save(orderHistory);
    }

}
