package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Tag(name = "payment", description = "결제 api")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 아이디 생성")
    @PostMapping
    ApiResponse<?> createPayment() {
        Long id = paymentService.createPayment();
        return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), id);
    }

}
