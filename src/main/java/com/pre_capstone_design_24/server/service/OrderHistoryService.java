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
import com.pre_capstone_design_24.server.responseDto.PagedResponseDto;
import com.pre_capstone_design_24.server.responseDto.PaymentResponseDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    private final OrderService orderService;

    private final PaymentService paymentService;

    private final NotificationService notificationService;

    public void createOrderHistory(OrderHistoryRequestDto orderHistoryRequestDto) {
        Long paymentId = orderHistoryRequestDto.getPaymentId();
        List<OrderRequestDto> orderRequestDtoList = orderHistoryRequestDto.getOrderRequestDtoList();
        List<Order> orders = orderService.toOrderList(orderRequestDtoList);
        orderService.saveOrders(orders);

        OrderHistory orderHistory = OrderHistory.builder()
                .payment(paymentService.findPaymentById(paymentId))
                .orderList(orders)
                .orderHistoryStatus(OrderHistoryStatus.NEW)
                .sumOfCost(orderService.getTotalCostOfOrders(orders))
                .build();

        orderService.setOrderHistoryId(orders, orderHistory);

        save(orderHistory);

        notificationService.sendUnreadOrderNotification(orderHistory.getId());
    }

    public PagedResponseDto<OrderHistoryResponseDto> searchOrderHistory(Pageable pageable, String keyword) {
        Page<OrderHistory> pagedOrderHistory = searchOrderHistoryByKeyword(pageable, keyword);
        return new PagedResponseDto<>(pagedOrderHistory.
                map(orderHistory -> makeOrderHistoryResponseDto(orderHistory)));
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

    public PaymentResponseDto getOrderHistoryByPaymentId(Long paymentId) {
        if (!paymentService.isPaymentExist(paymentId))
            throw new GeneralException(Status.PAYMENT_NOT_FOUND);

        List<OrderHistory> orderHistoryList = findOrderHistoryByPaymentId(paymentId);
        List<OrderHistoryResponseDto> orderHistoryResponseDtoList = makeListOfOrderHistoryResponseDto(orderHistoryList);
        Long sumOfCost = orderHistoryResponseDtoList.stream()
                .mapToLong(OrderHistoryResponseDto::getSumOfOrderHistoryCost)
                .sum();

        return PaymentResponseDto.builder()
                .paymentId(paymentId)
                .orderHistoryResponseDtoList(orderHistoryResponseDtoList)
                .sumOfPaymentCost(sumOfCost)
                .build();
    }

    public PagedResponseDto<OrderHistoryResponseDto> getOrderHistoryOrderByLatest(Pageable pageable) {
        Page<OrderHistory> pagedOrderHistory = getAllOrderHistoryOrderByCreatedAtDesc(pageable);
        return new PagedResponseDto<>(pagedOrderHistory.
                map(orderHistory -> makeOrderHistoryResponseDto(orderHistory)));
    }

    public PagedResponseDto<OrderHistoryResponseDto> getOrderHistoryOrderByStatus(OrderHistoryStatus orderHistoryStatus, Pageable pageable) {
        Page<OrderHistory> pagedOrderHistory = orderHistoryRepository.findAllByOrderHistoryStatus(orderHistoryStatus, pageable);
        return new PagedResponseDto<>(pagedOrderHistory
                .map(orderHistory -> makeOrderHistoryResponseDto(orderHistory)));
    }

    public PagedResponseDto<OrderHistoryResponseDto> getOrderHistoryOrderByDate(LocalDate localDate, Pageable pageable) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        Page<OrderHistory> pagedOrderHistory = findOrderHistoryByYearMonthDay(year, month, day, pageable);
        return new PagedResponseDto<>(pagedOrderHistory.
                map(orderHistory -> makeOrderHistoryResponseDto(orderHistory)));
    }

    public long getRevenueByYearMonth(int year, int month) {
        long revenue = 0;
        List<OrderHistory> orderHistoryList = findOrderHistoryByYearMonth(year, month);
        for(OrderHistory orderHistory : orderHistoryList) {
            List<Order> orderList = orderService.getOrdersByOrderHistoryId(orderHistory.getId());
            revenue += orderService.getTotalCostOfOrders(orderList);
        }
        return revenue;
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
                .sumOfOrderHistoryCost(orderHistory.getSumOfCost())
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
        return orderHistoryRepository.findAllByPaymentId(paymentId);
    }

    public Page<OrderHistory> searchOrderHistoryByKeyword(Pageable pageable, String keyword) {
        return orderHistoryRepository.searchOrderHistoryByKeyword(pageable, keyword);
    }

    public Page<OrderHistory> findOrderHistoryByYearMonthDay(int year, int month, int date, Pageable pageable) {
        return orderHistoryRepository.findByYearMonthDay(year, month, date, pageable);
    }

    public List<OrderHistory> findOrderHistoryByYearMonth(int year, int month) {
        return orderHistoryRepository.findAllByYearMonth(year, month);
    }

    public Page<OrderHistory> getAllOrderHistoryOrderByCreatedAtDesc(Pageable pageable) {
        return orderHistoryRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public void save(OrderHistory orderHistory) {
        orderHistoryRepository.save(orderHistory);
    }

}
