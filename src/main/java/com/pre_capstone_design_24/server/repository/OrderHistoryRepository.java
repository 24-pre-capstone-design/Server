package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.domain.OrderHistoryStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findAllByPaymentId (Long paymentId);

    List<OrderHistory> findAllByOrderByCreatedAtDesc();

    long countByOrderHistoryStatus(OrderHistoryStatus orderHistoryStatus);

    @Query("SELECT orderHistory FROM OrderHistory orderHistory WHERE FUNCTION('YEAR', orderHistory.createdAt) = :year AND FUNCTION('MONTH', orderHistory.createdAt) = :month AND FUNCTION('DAY', orderHistory.createdAt) = :day")
    List<OrderHistory> findByYearMonthDay(int year, int month, int day);

    @Query("SELECT orderHistory FROM OrderHistory orderHistory WHERE FUNCTION('YEAR', orderHistory.createdAt) = :year AND FUNCTION('MONTH', orderHistory.createdAt) = :month")

    List<OrderHistory> findAllByYearMonth(int year, int month);

}
