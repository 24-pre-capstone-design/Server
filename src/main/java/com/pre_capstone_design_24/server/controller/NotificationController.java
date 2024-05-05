package com.pre_capstone_design_24.server.controller;

import com.pre_capstone_design_24.server.domain.Notification;
import com.pre_capstone_design_24.server.global.response.ApiResponse;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
    @Secured({"ROLE_USER"})
    @GetMapping("/unread")
    public ApiResponse<?> getUnreadNotifications() {
        List<Notification> unreadNotifications = notificationService.getUnreadNotifications();
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), unreadNotifications);
    }

    @Operation(summary = "전체 알림 조회")
    @Secured({"ROLE_USER"})
    @GetMapping("/all")
    public ApiResponse<?> getAllNotifications() {
        List<Notification> allNotifications = notificationService.getAllNotifications();
        return ApiResponse.onSuccess(Status.OK.getCode(), Status.OK.getMessage(), allNotifications);
    }

    @Operation(summary = "모든 알림 삭제")
    @Secured({"ROLE_USER"})
    @DeleteMapping("/deleteAll")
    public ApiResponse<?> deleteAllNotifications() {
        notificationService.deleteAllNotifications();
        return ApiResponse.onSuccess(Status.OK.getCode(), "모든 알림이 삭제되었습니다.", null);
    }

    @Operation(summary = "알림 삭제")
    @Secured({"ROLE_USER"})
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ApiResponse.onSuccess(Status.OK.getCode(), "알림이 삭제되었습니다.", null);
    }

    @Operation(summary = "sse세션연결")
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal User principal,
        @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(principal.getUsername(), lastEventId);
    }
}
