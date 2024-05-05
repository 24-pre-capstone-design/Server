package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Food;
import com.pre_capstone_design_24.server.domain.FoodCategory;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.FoodCategoryRepository;
import com.pre_capstone_design_24.server.repository.FoodRepository;
import com.pre_capstone_design_24.server.requestDto.FoodCategoryRequestDto;
import com.pre_capstone_design_24.server.requestDto.FoodRequestDto;
import com.pre_capstone_design_24.server.responseDto.FoodCategoryResponseDto;
import com.pre_capstone_design_24.server.responseDto.FoodResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final FoodRepository foodRepository;

    public void createFoodCategory(FoodCategoryRequestDto foodCategoryRequestDto) {
        FoodCategory newFoodCategory = FoodCategory.of(foodCategoryRequestDto);
        save(newFoodCategory);
    }

    public void createFoodCategory(String foodCategoryName) {
        FoodCategory newFoodCategory = FoodCategory.of(foodCategoryName);
        save(newFoodCategory);
    }

    public FoodCategoryResponseDto getFoodCategory(Long id) {
        FoodCategory foodCategory = getFoodCategoryById(id);
        return FoodCategoryResponseDto.of(foodCategory);
    }

    public void updateFoodCategory(Long id, FoodCategoryRequestDto foodCategoryRequestDto) {
        FoodCategory foodCategory = getFoodCategoryById(id);
        foodCategory.update(foodCategoryRequestDto);
        save(foodCategory);
    }

    public void deleteFoodCategory(Long id) {
        FoodCategory foodCategory = getFoodCategoryById(id);
        disconnectWithFood(foodCategory);
        delete(foodCategory);
    }

    public void save(FoodCategory foodCategory) {
        foodCategoryRepository.save(foodCategory);
    }

    public void delete(FoodCategory foodCategory) {
        foodCategoryRepository.delete(foodCategory);
    }

    public FoodCategory getFoodCategoryById(Long id) {
        return foodCategoryRepository.findById(id)
                .orElseThrow(() -> new GeneralException(Status.FOOD_CATEGORY_NOT_FOUND));
    }

    public List<FoodCategoryResponseDto> getAllFoodCategories() {
        List<FoodCategory> foodCategories = foodCategoryRepository.findAll();
        return foodCategories.stream()
                .map(foodCategory -> FoodCategoryResponseDto.of(foodCategory))
                .toList();
    }

    public void disconnectWithFood(FoodCategory foodCategory) {
        for (Food food : foodRepository.findAll()) {
            if (food.getFoodCategory() != null && food.getFoodCategory().getId().equals(foodCategory.getId())) {
                food.updateFoodCategory(null);
            }
        }
    }

}
