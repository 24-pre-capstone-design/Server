package com.pre_capstone_design_24.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pre_capstone_design_24.server.requestDto.OrderHistoryRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "orderHistory")
    private List<Order> orderList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private OrderHistoryStatus orderHistoryStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    public void updateOrderHistoryStatus(OrderHistoryStatus orderHistoryStatus) {
        this.orderHistoryStatus = orderHistoryStatus;
    }

}
