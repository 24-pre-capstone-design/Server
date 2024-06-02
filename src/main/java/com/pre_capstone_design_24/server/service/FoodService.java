package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Food;
import com.pre_capstone_design_24.server.domain.FoodCategory;
import com.pre_capstone_design_24.server.domain.Notification;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.FoodRepository;
import com.pre_capstone_design_24.server.requestDto.FoodRequestDto;
import com.pre_capstone_design_24.server.responseDto.FoodResponseDto;
import com.pre_capstone_design_24.server.responseDto.NotificationResponseDto;
import com.pre_capstone_design_24.server.responseDto.PagedResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodCategoryService foodCategoryService;

    public void createFood(FoodRequestDto foodRequestDto) {
        Food newFood = Food.of(foodRequestDto);
        newFood.updateFoodCategory(foodCategoryService.getFoodCategoryById(foodRequestDto.getFoodCategoryId()));
        save(newFood);
    }

    public FoodResponseDto getFood(Long id) {
        Food food = getFoodById(id);
        return FoodResponseDto.of(food);
    }

    public PagedResponseDto<FoodResponseDto> getFoodByCreatedAt(Pageable pageable) {
        Page<Food> foods = foodRepository.findAllByOrderByCreatedAt(pageable);
        return new PagedResponseDto<>(foods.map(this::makeFoodResponseDto));
    }


    public void updateFood(Long id, FoodRequestDto foodRequestDto) {
        Food food = getFoodById(id);
        food.update(foodRequestDto);
        food.updateFoodCategory(foodCategoryService.getFoodCategoryById(foodRequestDto.getFoodCategoryId()));
        save(food);
    }

    public void deleteFood(Long id) {
        Food food = getFoodById(id);
        delete(food);
    }

    public void save(Food food) {
        foodRepository.save(food);
    }

    public void delete(Food food) {
        foodRepository.delete(food);
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Status.FOOD_NOT_FOUND));
    }

    public List<FoodResponseDto> getAllFoods() {
        List<Food> foods = foodRepository.findAll();
        return foods.stream()
                .map(food -> FoodResponseDto.of(food))
                .toList();
    }

    public List<FoodResponseDto> getFoodsByFoodCategoryId(Long foodCategoryId) {
        FoodCategory foodCategory = null;

        if (foodCategoryId != null) {
             foodCategory = foodCategoryService.getFoodCategoryById(foodCategoryId);
        }

        List<Food> foods = foodRepository.findByFoodCategory(foodCategory);
        return foods.stream()
                .map(food -> FoodResponseDto.of(food))
                .toList();
    }

    public List<FoodResponseDto> getFoodsByFKeyword(String keyword) {
        List<Food> foods;
        if (keyword == null) {
            foods = foodRepository.findAll();
        } else {
            foods = new ArrayList<>();
            for (Food food : foodRepository.findAll()) {
                if (food.getName().contains(keyword)) {
                    foods.add(food);
                }
            }
        }

        return foods.stream()
                .map(food -> FoodResponseDto.of(food))
                .toList();
    }

    public FoodResponseDto makeFoodResponseDto(Food food) {
        return FoodResponseDto.of(food);
    }

}
