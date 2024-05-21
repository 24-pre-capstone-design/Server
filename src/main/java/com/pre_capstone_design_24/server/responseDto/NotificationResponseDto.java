package com.pre_capstone_design_24.server.responseDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pre_capstone_design_24.server.domain.Notification;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NotificationResponseDto {

    private Long id;

    private String title;

    private String content;

    private boolean read;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static NotificationResponseDto createResponse(Notification notification) {
        return NotificationResponseDto.builder()
            .id(notification.getId())
            .title(notification.getTitle())
            .content(notification.getContent())
            .createdAt(LocalDateTime.parse(notification.getCreatedAt().toString()))
            .read(notification.isRead())
            .build();
    }
}
