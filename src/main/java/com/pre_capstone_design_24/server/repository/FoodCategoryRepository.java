package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
}
