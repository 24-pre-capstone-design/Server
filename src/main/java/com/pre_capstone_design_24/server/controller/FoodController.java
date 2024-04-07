package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.domain.Food;
import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.FoodRepository;
import com.pre_capstone_design_24.server.requestDto.FoodRequestDto;
import com.pre_capstone_design_24.server.responseDto.FoodResponseDto;
import com.pre_capstone_design_24.server.service.FoodService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@Tag(name = "food", description = "음식 api")
public class FoodController {

    private final FoodService foodService;
    private final FoodRepository foodRepository;

    @Operation(summary = "음식 생성")
    @PostMapping("")
    public ApiResponse<?> createFood(
            @RequestBody FoodRequestDto foodRequestDto
    ) {
        foodService.createFood(foodRequestDto);
        return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), null);
    }

    @Operation(summary = "음식 조회")
    @GetMapping("/{foodId}")
    public ApiResponse<FoodResponseDto> getFood(
            @PathVariable("foodId") Long foodId
    ) {
        FoodResponseDto foodResponseDto = foodService.getFood(foodId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), foodResponseDto);
    }

    @Operation(summary = "음식 수정")
    @PatchMapping("/{foodId}")
    public ApiResponse<?> updateFood(
            @PathVariable("foodId") Long foodId,
            @RequestBody FoodRequestDto foodRequestDto
    ) {
        foodService.updateFood(foodId, foodRequestDto);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    @Operation(summary = "음식 삭제")
    @DeleteMapping("/{foodId}")
    public ApiResponse<?> deleteFood(
            @PathVariable("foodId") Long foodId
    ) {
        foodService.deleteFood(foodId);
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), null);
    }

    @Operation(summary = "전체 음식 조회")
    @GetMapping("")
    public ApiResponse<List<FoodResponseDto>> getAllFoods() {
        List<FoodResponseDto> foodResponseDtos = new ArrayList<>();
        for (Food food : foodRepository.findAll()) {
            foodResponseDtos.add(FoodResponseDto.of(food));
        }
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), foodResponseDtos);
    }

    @Operation(summary = "카테고리 id별 음식 조회")
    @GetMapping("/{foodCategoryId}")
    public ApiResponse<List<FoodResponseDto>> getFoodsByFoodCategoryId(
            @PathVariable("foodCategoryId")
            @RequestParam(required = false)
            Long foodCategoryId
    ) {
        List<FoodResponseDto> foodResponseDtos = new ArrayList<>();

        for (Food food : foodRepository.findAll()) {
            if (foodCategoryId == null && food.getFoodCategory() == null) {
                foodResponseDtos.add(FoodResponseDto.of(food));
            } else if (food.getFoodCategory() != null && food.getFoodCategory().getId().equals(foodCategoryId)) {
                foodResponseDtos.add(FoodResponseDto.of(food));
            }
        }

        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), foodResponseDtos);
    }

}