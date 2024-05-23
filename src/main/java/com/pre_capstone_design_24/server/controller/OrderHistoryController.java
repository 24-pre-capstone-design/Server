package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.domain.OrderHistoryStatus;
import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.OrderHistoryRequestDto;
import com.pre_capstone_design_24.server.responseDto.OrderHistoryResponseDto;
import com.pre_capstone_design_24.server.responseDto.PagedResponseDto;
import com.pre_capstone_design_24.server.responseDto.PaymentResponseDto;
import com.pre_capstone_design_24.server.service.OrderHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderHistory")
@RequiredArgsConstructor
@Tag(name = "orderHistory", description = "주문내역 api")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @Operation(summary = "주문내역 생성")
    @PostMapping("")
    public ApiResponse<?> createOrderHistory(
            @RequestBody OrderHistoryRequestDto orderHistoryRequestDto) {
        orderHistoryService.createOrderHistory(orderHistoryRequestDto);
        return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), null);
    }

    @Operation(summary = "키워드로 주문내역 검색하기")
    @Secured({"ROLE_USER"})
    @GetMapping("/search")
    public ApiResponse<?> searchOrderHistory(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponseDto<OrderHistoryResponseDto> orderHistoryResponseDtoList = orderHistoryService.searchOrderHistory(pageable, keyword);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), orderHistoryResponseDtoList);
    }

    @Operation(summary = "주문내역 조회")
    @GetMapping("/get/{orderHistoryId}")
    public ApiResponse<?> getOrderHistory(
            @PathVariable Long orderHistoryId) {
        OrderHistoryResponseDto orderHistoryResponseDto = orderHistoryService.getOrderHistory(orderHistoryId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), orderHistoryResponseDto);    }

    @Operation(summary = "주문내역 상태 수정")
    @Secured({"ROLE_USER"})
    @PatchMapping("/{orderHistoryId}")
    public ApiResponse<?> updateOrderHistoryStatus(
            @PathVariable Long orderHistoryId,
            @RequestParam OrderHistoryStatus orderHistoryStatus) {
        orderHistoryService.updateOrderHistoryStatus(orderHistoryId, orderHistoryStatus);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    @Operation(summary = "결제번호로 주문내역 조회")
    @GetMapping("/payment/{paymentId}")
    public ApiResponse<?> getOrderHistoryByPaymentId(
            @PathVariable Long paymentId) {
        PaymentResponseDto paymentResponseDto = orderHistoryService.getOrderHistoryByPaymentId(paymentId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), paymentResponseDto);
    }

    @Operation(summary = "최신순으로 주문내역 전체 조회")
    @Secured({"ROLE_USER"})
    @GetMapping("/latest")
    public ApiResponse<?> getOrderHistoryOrderByLatest(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponseDto<OrderHistoryResponseDto> orderHistoryResponseDtoList = orderHistoryService.getOrderHistoryOrderByLatest(pageable);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), orderHistoryResponseDtoList);
    }

    @Operation(summary = "주문상태별로 주문내역 조회")
    @Secured({"ROLE_USER"})
    @GetMapping("/{orderHistoryStatus}")
    public ApiResponse<?> getOrderHistoryOrderByStatus(
            @PathVariable("orderHistoryStatus") OrderHistoryStatus orderHistoryStatus,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponseDto<OrderHistoryResponseDto> orderHistoryResponseDtoList = orderHistoryService.getOrderHistoryOrderByStatus(orderHistoryStatus,pageable);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), orderHistoryResponseDtoList);
    }

    @Operation(summary = "날짜로 주문내역 조회")
    @Secured({"ROLE_USER"})
    @GetMapping("/date")
    public ApiResponse<?> getOrderHistoryByDate(
            @RequestParam LocalDate date,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PagedResponseDto<OrderHistoryResponseDto> orderHistoryResponseDtoList = orderHistoryService.getOrderHistoryOrderByDate(date, pageable);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), orderHistoryResponseDtoList);
    }

    @Operation(summary = "월 매출액 조회")
    @Secured({"ROLE_USER"})
    @GetMapping("/month")
    public ApiResponse<?> getRevenueByMonth(
            @RequestParam int year,
            @RequestParam int month) {
        long revenue = orderHistoryService.getRevenueByYearMonth(year, month);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), revenue);
    }

    @Operation(summary = "신규 주문내역 개수 조회")
    @Secured({"ROLE_USER"})
    @GetMapping("/new")
    public ApiResponse<?> getNumberOfNEWOrderHistory() {
        long numberOfNEWOrderHistory = orderHistoryService.getNumberOfNEWOrderHistory(OrderHistoryStatus.NEW);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), numberOfNEWOrderHistory);
    }



}
