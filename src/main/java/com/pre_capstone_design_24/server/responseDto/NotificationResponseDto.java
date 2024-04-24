package com.pre_capstone_design_24.server.responseDto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponseDto {

    private Long id;

    private String title;

    private String content;

    private boolean read;

    private LocalDateTime createdAt;

}
