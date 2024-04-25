package com.pre_capstone_design_24.server.service;

import com.pre_capstone_design_24.server.domain.Employee;
import com.pre_capstone_design_24.server.domain.Notification;
import com.pre_capstone_design_24.server.domain.Order;
import com.pre_capstone_design_24.server.domain.OrderHistory;
import com.pre_capstone_design_24.server.domain.Owner;
import com.pre_capstone_design_24.server.global.response.GeneralException;
import com.pre_capstone_design_24.server.global.response.Status;
import com.pre_capstone_design_24.server.repository.NotificationRepository;
import com.pre_capstone_design_24.server.repository.OrderHistoryRepository;
import com.pre_capstone_design_24.server.requestDto.NotificationRequestDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    public void sendUnreadOrderNotification(Long orderHistoryId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId)
            .orElseThrow(() -> new GeneralException(Status.ORDERHISTORY_NOT_FOUND));

        List<Order> orders = orderHistory.getOrderList();
        StringBuilder contentBuilder = new StringBuilder("주문 ID: ");
        contentBuilder.append(orderHistoryId).append(", ");

        for (Order order : orders) {
            contentBuilder.append("상품: ").append(order.getFood().getName()).append(", ");
            contentBuilder.append("수량: ").append(order.getQuantity());
        }

        String content = contentBuilder.toString();

        LocalDateTime createdAt = LocalDateTime.now();

        Notification notification = Notification.builder()
            .title("새 주문이 도착했습니다.")
            .content(content)
            .createdAt(createdAt)
            .build();

        save(notification);
    }


    public List<Notification> getUnreadNotifications() {
        List<Notification> unreadNotifications = notificationRepository.findByReadFalseOrderByCreatedAtDesc();

        for (Notification notification : unreadNotifications) {
            notification.setRead(true);
            save(notification);
        }

        return unreadNotifications;
    }

    public List<Notification> getAllNotifications() {
        List<Notification> unreadNotifications = notificationRepository.findByReadFalseOrderByCreatedAtDesc();

        for (Notification notification : unreadNotifications) {
            notification.setRead(true);
            save(notification);
        }

        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new GeneralException(Status.NOTIFICATION_NOT_FOUND));

        delete(notification);
    }

    public void deleteAllNotifications() {
        deleteAll();
    }

    public void delete(Notification notificationId) {
        notificationRepository.delete(notificationId);
    }

    public void deleteAll() {
        notificationRepository.deleteAll();
    }
}