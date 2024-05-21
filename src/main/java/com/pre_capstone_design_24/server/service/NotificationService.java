package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Notification;
import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.EmitterRepository;
import com.pre_capstone_design_24.server.repository.NotificationRepository;
import com.pre_capstone_design_24.server.repository.OrderHistoryRepository;
import com.pre_capstone_design_24.server.responseDto.NotificationResponseDto;
import com.pre_capstone_design_24.server.responseDto.PagedResponseDto;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@AllArgsConstructor
@Service
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    private final EmitterRepository emitterRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    public SseEmitter subscribe(String username, String lastEventId) {
        String emitterId = username + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        sendNotification(emitter, emitterId, "EventStream Created. [userEmail=" + username + "]");

        if (!lastEventId.isEmpty()) {
            resendLostData(lastEventId, username, emitter);
        }
        return emitter;
    }

    public void sendUnreadOrderNotification(Long orderHistoryId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId)
            .orElseThrow(() -> new GeneralException(Status.ORDERHISTORY_NOT_FOUND));

        String content = buildOrderNotificationContent(orderHistory);
        Notification notification = createNotification("새 주문이 도착했습니다.", content);

        notification = save(notification);
        notifySubscribers(notification, orderHistory.getId());
    }

    private String buildOrderNotificationContent(OrderHistory orderHistory) {
        StringBuilder contentBuilder = new StringBuilder("주문 ID: ");
        contentBuilder.append(orderHistory.getId()).append(", ");

        for (Order order : orderHistory.getOrderList()) {
            contentBuilder.append("상품: ").append(order.getFood().getName()).append(", ");
            contentBuilder.append("수량: ").append(order.getQuantity()).append("; ");
        }
        return contentBuilder.toString();
    }

    private Notification createNotification(String title, String content) {
        return Notification.builder()
            .title(title)
            .content(content)
            .createdAt(LocalDateTime.now())
            .build();
    }

    private void notifySubscribers(Notification notification, Long userId) {
        String eventId = userId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(String.valueOf(userId));
        emitters.forEach((key, emitter) -> {
            saveEventCache(key, notification);
            sendNotification(emitter, eventId, NotificationResponseDto.createResponse(notification));
        });
    }

    private void sendNotification(SseEmitter emitter, String eventId, Object data) {
        try {
            emitter.send(SseEmitter.event().id(eventId).name("notification").data(data));
            System.out.println("알림 전송 성공: " + data); // 출력 변경
        } catch (IOException e) {
            System.err.println("SSE 전송 실패: " + e.getMessage()); // 출력 변경
        }
    }

    private void resendLostData(String lastEventId, String userEmail, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(userEmail);
        eventCaches.entrySet().stream()
            .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
            .forEach(entry -> sendNotification(emitter, entry.getKey(), entry.getValue()));
    }

    private void saveEventCache(String key, Notification notification) {
        emitterRepository.saveEventCache(key, notification);
    }

    public List<Notification> getUnreadNotifications() {
        List<Notification> unreadNotifications = notificationRepository.findByReadFalseOrderByCreatedAtDesc();

        for (Notification notification : unreadNotifications) {
            notification.setRead(true);
            save(notification);
        }

        return unreadNotifications;
    }

    public PagedResponseDto<NotificationResponseDto> getNotificationsByUnread(Pageable pageable) {
        Page<Notification> pagedNotification = notificationRepository.findByReadFalseOrderByCreatedAtDesc(pageable);

        for (Notification notification : pagedNotification) {
            notification.setRead(true);
            save(notification);
        }

        return new PagedResponseDto<>(pagedNotification.map(this::makeNotificationResponseDto));
    }

    public PagedResponseDto<NotificationResponseDto> getAllNotifications(Pageable pageable) {
        Page<Notification> pagedNotification = notificationRepository.findAllByOrderByCreatedAtDesc(pageable);
        return new PagedResponseDto<>(pagedNotification.map(this::makeNotificationResponseDto));
    }

    public NotificationResponseDto makeNotificationResponseDto(Notification notification) {
        return NotificationResponseDto.createResponse(notification);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new GeneralException(Status.NOTIFICATION_NOT_FOUND));
        notificationRepository.delete(notification);
    }

    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }
}
