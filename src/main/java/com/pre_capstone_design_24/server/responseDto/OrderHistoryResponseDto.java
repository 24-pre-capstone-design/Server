package com.pre_capstone_design_24.server.responseDto;

import com.pre_capstone_design_24.server.domain.OrderHistoryStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderHistoryResponseDto {

    private Long paymentId;

    private List<OrderResponseDto> orderResponseDtoList;

    private OrderHistoryStatus orderHistoryStatus;

    private LocalDateTime orderedAt;

}
