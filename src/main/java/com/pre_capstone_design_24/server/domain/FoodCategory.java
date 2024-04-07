package com.pre_capstone_design_24.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pre_capstone_design_24.server.requestDto.FoodCategoryRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String name;

    @OneToMany(mappedBy = "foodCategoryId", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();

    public static FoodCategory of(FoodCategoryRequestDto foodCategoryRequestDto) {
        return FoodCategory.builder()
                .name(foodCategoryRequestDto.getName())
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void update(FoodCategoryRequestDto foodCategoryRequestDto) {
        updateName(foodCategoryRequestDto.getName());
    }

}