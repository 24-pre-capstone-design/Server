package com.pre_capstone_design_24.server.repository;

import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.domain.OrderHistoryStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findAllByPaymentId (Long paymentId);

    Page<OrderHistory> findAllByOrderByCreatedAtDesc(Pageable pageable);

    long countByOrderHistoryStatus(OrderHistoryStatus orderHistoryStatus);

    @Query("SELECT orderHistory FROM OrderHistory orderHistory WHERE FUNCTION('YEAR', orderHistory.createdAt) = :year AND FUNCTION('MONTH', orderHistory.createdAt) = :month AND FUNCTION('DAY', orderHistory.createdAt) = :day")
    Page<OrderHistory> findByYearMonthDay(int year, int month, int day, Pageable pageable);

    @Query("SELECT orderHistory FROM OrderHistory orderHistory WHERE FUNCTION('YEAR', orderHistory.createdAt) = :year AND FUNCTION('MONTH', orderHistory.createdAt) = :month")

    List<OrderHistory> findAllByYearMonth(int year, int month);

    @Query("SELECT oh FROM OrderHistory oh WHERE oh.createdAt >= :startDateTime")
    List<OrderHistory> findOrderHistoriesFromDateTime(@Param("startDateTime") LocalDateTime startDateTime);

    @Query("SELECT oh FROM OrderHistory oh WHERE oh.createdAt BETWEEN :startDateTime AND :endDateTime")
    Page<OrderHistory> findByPeriod(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            Pageable pageable);

    @Query("SELECT oh FROM OrderHistory oh WHERE oh.createdAt BETWEEN :startDateTime AND :endDateTime")
    List<OrderHistory> findOrderHistoriesBetweenDates(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT oh FROM OrderHistory oh WHERE FUNCTION('DATE', oh.createdAt) = :date")
    List<OrderHistory> findAllByDate(@Param("date") LocalDate date);

    Page<OrderHistory> findAllByOrderHistoryStatus(OrderHistoryStatus orderHistoryStatus, Pageable pageable);

    @Query("SELECT oh FROM OrderHistory oh JOIN oh.orderList o WHERE o.food.name LIKE %:keyword%")
    Page<OrderHistory> searchOrderHistoryByKeyword(Pageable pageable, String keyword);

}
