package com.pre_capstone_design_24.server.domain;

import com.pre_capstone_design_24.server.repository.FoodCategoryRepository;
import com.pre_capstone_design_24.server.requestDto.FoodRequestDto;
import com.pre_capstone_design_24.server.service.FoodCategoryService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_category_id")
    private FoodCategory foodCategory;

    @NotNull
    private String name;

    @NotNull
    private int price;

    @NotNull
    private String pictureURL;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private FoodStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Food of(FoodRequestDto foodRequestDto) {
        return Food.builder()
                .name(foodRequestDto.getName())
                .price(foodRequestDto.getPrice())
                .pictureURL(foodRequestDto.getPictureURL())
                .status(foodRequestDto.getStatus())
                .build();
    }

    public void updateFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
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
        updateName(foodRequestDto.getName());
        updatePrice(foodRequestDto.getPrice());
        updatePictureURL(foodRequestDto.getPictureURL());
        updateStatus(foodRequestDto.getStatus());
    }

}