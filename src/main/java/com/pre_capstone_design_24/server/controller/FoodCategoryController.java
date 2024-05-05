package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.requestDto.FoodCategoryRequestDto;
import com.pre_capstone_design_24.server.responseDto.FoodCategoryResponseDto;
import com.pre_capstone_design_24.server.service.FoodCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foodCategory")
@RequiredArgsConstructor
@Tag(name = "foodCategory", description = "음식 카테고리 api")
public class FoodCategoryController {

    private final FoodCategoryService foodCategoryService;

    @Operation(summary = "음식 카테고리 생성")
    @Secured({"ROLE_USER"})
    @PostMapping("")
    public ApiResponse<?> createFoodCategory(
            @RequestBody FoodCategoryRequestDto foodCategoryRequestDto
    ) {
        foodCategoryService.createFoodCategory(foodCategoryRequestDto);
        return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), null);
    }

    @Operation(summary = "음식 카테고리 조회")
    @GetMapping("/{foodCategoryId}")
    public ApiResponse<FoodCategoryResponseDto> getFoodCategory(
            @PathVariable("foodCategoryId") Long foodCategoryId
    ) {
        FoodCategoryResponseDto foodCategoryResponseDto = foodCategoryService.getFoodCategory(foodCategoryId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), foodCategoryResponseDto);
    }

    @Operation(summary = "음식 카테고리 수정")
    @Secured({"ROLE_USER"})
    @PatchMapping("/{foodCategoryId}")
    public ApiResponse<?> updateFoodCategory(
            @PathVariable("foodCategoryId") Long foodCategoryId,
            @RequestBody FoodCategoryRequestDto foodCategoryRequestDto
    ) {
        foodCategoryService.updateFoodCategory(foodCategoryId, foodCategoryRequestDto);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    @Operation(summary = "음식 카테고리 삭제")
    @Secured({"ROLE_USER"})
    @DeleteMapping("/{foodCategoryId}")
    public ApiResponse<?> deleteFoodCategory(
            @PathVariable("foodCategoryId") Long foodCategoryId
    ) {
        foodCategoryService.deleteFoodCategory(foodCategoryId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    @Operation(summary = "전체 음식 카테고리 조회")
    @GetMapping("")
    public ApiResponse<List<FoodCategoryResponseDto>> getAllFoodCategories() {
        List<FoodCategoryResponseDto> foodCategoryResponseDtos = foodCategoryService.getAllFoodCategories();
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), foodCategoryResponseDtos);
    }

}
