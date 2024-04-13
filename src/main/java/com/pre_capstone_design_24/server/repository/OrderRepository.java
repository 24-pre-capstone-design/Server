package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderHistoryId(Long id);

}
