package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.domain.Notification;
import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "notification", description = "알림")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "새 주문 알림 생성")
    @PostMapping("/newOrder")
    public ApiResponse<?> createNewOrderNotification(
        @RequestParam Long orderHistoryId)
        {
            notificationService.sendUnreadOrderNotification(orderHistoryId);
            return ApiResponse.onSuccess(Status.CREATED.getCode(), Status.CREATED.getMessage(), null);
    }

    @Operation(summary = "읽지 않은 알림 조회")
    @GetMapping("/unread")
    public ApiResponse<?> getUnreadNotifications() {
        List<Notification> unreadNotifications = notificationService.getUnreadNotifications();
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), unreadNotifications);
    }

    @Operation(summary = "전체 알림 조회")
    @GetMapping("/all")
    public ApiResponse<?> getAllNotifications() {
        List<Notification> allNotifications = notificationService.getAllNotifications();
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), allNotifications);
    }
}
