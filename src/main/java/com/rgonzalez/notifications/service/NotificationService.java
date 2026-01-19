package com.rgonzalez.notifications.service;

import com.rgonzalez.notifications.dto.NotificationRequest;
import com.rgonzalez.notifications.dto.NotificationResponse;
import com.rgonzalez.notifications.model.Notification;
import com.rgonzalez.notifications.model.NotificationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class NotificationService {

    private final ConcurrentHashMap<String, Notification> notificationStore = new ConcurrentHashMap<>();

    public NotificationResponse sendNotification(NotificationRequest request) {
        log.info("Sending notification to: {}", request.getRecipient());
        
        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .message(request.getMessage())
                .type(request.getType())
                .status(NotificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        // In a real application, this would integrate with email/SMS/push notification services
        // For now, we simulate a successful send immediately
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        log.info("Notification sent successfully: {}", notification.getId());

        notificationStore.put(notification.getId(), notification);
        return mapToResponse(notification);
    }

    public List<NotificationResponse> getAllNotifications() {
        log.info("Retrieving all notifications");
        return notificationStore.values().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Optional<NotificationResponse> getNotificationById(String id) {
        log.info("Retrieving notification by id: {}", id);
        return Optional.ofNullable(notificationStore.get(id))
                .map(this::mapToResponse);
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .recipient(notification.getRecipient())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .type(notification.getType())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .build();
    }
}
