package com.pre_capstone_design_24.server.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryRequestDto {

    @NotNull
    @Schema(description = "결제 아이디")
    Long paymentId;

    @NotNull
    @Schema(description = "음식 아이디 리스트")
    List<OrderRequestDto> orderRequestDtoList;

}
