package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.OrderHistoryRequestDto;
import com.pre_capstone_design_24.server.service.OrderHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderHistory")
@RequiredArgsConstructor
@Tag(name = "orderHistory", description = "주문내역 api")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    @Operation(summary = "주문내역 생성")
    @PostMapping("")
    public ApiResponse<?> createOrderHistory(@RequestBody OrderHistoryRequestDto orderHistoryRequestDto) {
        orderHistoryService.createOrderHistory(orderHistoryRequestDto);
        return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), null);
    }

}
