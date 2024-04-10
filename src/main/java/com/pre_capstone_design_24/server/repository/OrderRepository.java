package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
