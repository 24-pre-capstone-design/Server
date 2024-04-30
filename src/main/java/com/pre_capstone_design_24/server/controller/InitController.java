package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.service.InitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/init")
@Tag(name = "init", description = "데이터 세팅 api")
public class InitController {

    private final InitService initService;

    @GetMapping("/foodCategories")
    @Operation(summary = "DB에 food category 적재")
    public ApiResponse<?> addCategories() throws IOException {
        initService.addCategories();
        return ApiResponse.onSuccess(Status.OK.getCode(),
                Status.CREATED.getMessage(), null);
    }

    @GetMapping("/foods")
    @Operation(summary = "DB에 food 적재")
    public ApiResponse<?> addFoods() throws IOException {
        initService.addFoods();
        return ApiResponse.onSuccess(Status.OK.getCode(),
                Status.CREATED.getMessage(), null);
    }

    @GetMapping("/orderHistories")
    @Operation(summary = "DB에 orderHistory 적재")
    public ApiResponse<?> addOrderHistories() throws IOException {
        initService.addOrderHistories();
        return ApiResponse.onSuccess(Status.OK.getCode(),
                Status.CREATED.getMessage(), null);
    }


}
