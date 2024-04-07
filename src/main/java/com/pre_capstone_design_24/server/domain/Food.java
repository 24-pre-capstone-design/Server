package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.requestDto.FoodRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long foodCategoryId;

    @NotNull
    private String name;

    @NotNull
    private int price;

    @NotNull
    private String pictureURL;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private FoodStatus status;

    @NotNull
    private LocalDateTime createdAt;

    public static Food of(FoodRequestDto foodRequestDto) {
        return Food.builder()
                .foodCategoryId(foodRequestDto.getFoodCategoryId())
                .name(foodRequestDto.getName())
                .price(foodRequestDto.getPrice())
                .pictureURL(foodRequestDto.getPictureURL())
                .status(foodRequestDto.getStatus())
                .createdAt(foodRequestDto.getCreatedAt())
                .build();
    }

    public void updateFoodCategoryId(Long foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public void updatePictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public void updateStatus(FoodStatus status) {
        this.status = status;
    }

    public void updateCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void update(FoodRequestDto foodRequestDto) {
        updateFoodCategoryId(foodRequestDto.getFoodCategoryId());
        updateName(foodRequestDto.getName());
        updatePrice(foodRequestDto.getPrice());
        updatePictureURL(foodRequestDto.getPictureURL());
        updateStatus(foodRequestDto.getStatus());
        updateCreatedAt(foodRequestDto.getCreatedAt());
    }

}