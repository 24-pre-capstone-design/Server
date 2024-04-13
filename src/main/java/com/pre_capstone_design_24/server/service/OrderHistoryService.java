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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
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

    public OrderHistoryResponseDto getOrderHistory(Long orderHistoryId) {
        OrderHistory orderHistory = getOrderHistoryById(orderHistoryId);
        return makeOrderHistoryResponseDto(orderHistory);
    }

    public void updateOrderHistoryStatus(Long orderHistoryId, OrderHistoryStatus orderHistoryStatus) {
        OrderHistory orderHistory = getOrderHistoryById(orderHistoryId);
        orderHistory.updateOrderHistoryStatus(orderHistoryStatus);
        save(orderHistory);
    }

    public List<OrderHistoryResponseDto> getOrderHistoryByPaymentId(Long paymentId) {
        if (!paymentService.isPaymentExist(paymentId))
            throw new GeneralException(Status.PAYMENT_NOT_FOUND);
        List<OrderHistory> orderHistoryList = findOrderHistoryByPaymentId(paymentId);
        return makeListOfOrderHistoryResponseDto(orderHistoryList);
    }

    public List<OrderHistoryResponseDto> getOrderHistoryOrderByLatest() {
        List<OrderHistory> orderHistoryList = getAllOrderHistoryOrderByCreatedAtDesc();
        return makeListOfOrderHistoryResponseDto(orderHistoryList);
    }

    public List<OrderHistoryResponseDto> getOrderHistoryOrderByDate(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        List<OrderHistory> orderHistoryList = findByYearMonthDay(year, month, day);
        return makeListOfOrderHistoryResponseDto(orderHistoryList);
    }

    public long getNumberOfNEWOrderHistory(OrderHistoryStatus status) {
        return getNumberOfOrderHistoryByOrderHistoryStatus(status);
    }

    public List<OrderHistoryResponseDto> makeListOfOrderHistoryResponseDto(List<OrderHistory> orderHistoryList) {
        List<OrderHistoryResponseDto> orderHistoryResponseDtoList = new ArrayList<>();
        for (OrderHistory history : orderHistoryList) {
            OrderHistoryResponseDto orderHistoryResponseDto = makeOrderHistoryResponseDto(history);
            orderHistoryResponseDtoList.add(orderHistoryResponseDto);
        }
        return orderHistoryResponseDtoList;
    }

    public OrderHistoryResponseDto makeOrderHistoryResponseDto(OrderHistory orderHistory) {
        List<OrderResponseDto> orderResponseDtoList = orderService.toOrderResponseList(orderHistory.getOrderList());
        OrderHistoryResponseDto orderHistoryResponseDto = OrderHistoryResponseDto.builder()
                .paymentId(orderHistory.getPayment().getId())
                .orderResponseDtoList(orderResponseDtoList)
                .orderHistoryStatus(orderHistory.getOrderHistoryStatus())
                .orderedAt(orderHistory.getCreatedAt())
                .build();
        return orderHistoryResponseDto;
    }

    public OrderHistory getOrderHistoryById(Long id) {
        return orderHistoryRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Status.ORDERHISTORY_NOT_FOUND));
    }

    public long getNumberOfOrderHistoryByOrderHistoryStatus(OrderHistoryStatus status) {
        return orderHistoryRepository.countByOrderHistoryStatus(status);
    }

    public List<OrderHistory> findOrderHistoryByPaymentId(Long paymentId) {
        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAllByPaymentId(paymentId);
        return orderHistoryList;
    }

    public List<OrderHistory> findByYearMonthDay(int year, int month, int date) {
        return orderHistoryRepository.findByYearMonthDay(year, month, date);
    }

    public List<OrderHistory> getAllOrderHistoryOrderByCreatedAtDesc() {
        return orderHistoryRepository.findAllByOrderByCreatedAtDesc();
    }

    public void save(OrderHistory orderHistory) {
        orderHistoryRepository.save(orderHistory);
    }

}
