package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.FoodCategory;
import com.pre_capstone_design_24.server.domain.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderHistoryId(Long id);

    @Query("SELECT o FROM Order o WHERE o.food.foodCategory.id = :categoryId")
    List<Order> findAllByFoodCategory(@Param("categoryId") Long categoryId);

}
