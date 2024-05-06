package com.pre_capstone_design_24.server.responseDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaymentResponseDto {

    private Long paymentId;

    private List<OrderHistoryResponseDto> orderHistoryResponseDtoList;

    private Long sumOfPaymentCost;

}
