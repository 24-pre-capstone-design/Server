package com.pre_capstone_design_24.server.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDto {

    @NotNull
    @Schema(description = "알림 제목", example = "주문 내역")
    private String title;

    @NotNull
    @Schema(description = "알림 내용", example = "참치김밥 1개")
    private String content;
}
