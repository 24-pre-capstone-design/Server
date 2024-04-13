package com.pre_capstone_design_24.server.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pre_capstone_design_24.server.domain.OrderHistoryStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class OrderHistoryResponseDto {

    private Long paymentId;

    private List<OrderResponseDto> orderResponseDtoList;

    private OrderHistoryStatus orderHistoryStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderedAt;

}
