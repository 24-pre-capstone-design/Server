package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.OrderHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findAllByPaymentId (Long paymentId);

}
