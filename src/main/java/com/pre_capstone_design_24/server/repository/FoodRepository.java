package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}