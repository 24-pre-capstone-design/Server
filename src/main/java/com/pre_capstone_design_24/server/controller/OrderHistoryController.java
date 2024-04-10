package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.OrderHistoryRequestDto;
import com.pre_capstone_design_24.server.responseDto.OrderHistoryResponseDto;
import com.pre_capstone_design_24.server.service.OrderHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "결제번호로 주문내역 조회")
    @GetMapping("/payment/{paymentId}")
    public ApiResponse<?> getOrderHistoryByPaymentId(
            @PathVariable Long paymentId) {
        List<OrderHistoryResponseDto> orderHistoryResponseDtoList = orderHistoryService.getOrderHistoryByPaymentId(paymentId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), orderHistoryResponseDtoList);
    }


}
