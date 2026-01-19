package com.rgonzalez.notifications.controller;

import com.rgonzalez.notifications.dto.NotificationRequest;
import com.rgonzalez.notifications.dto.NotificationResponse;
import com.rgonzalez.notifications.model.NotificationType;
import com.rgonzalez.notifications.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Test
    void shouldSendNotification() throws Exception {
        NotificationResponse response = NotificationResponse.builder()
                .id("test-id")
                .recipient("test@example.com")
                .subject("Test Subject")
                .message("Test Message")
                .type(NotificationType.EMAIL)
                .build();

        when(notificationService.sendNotification(any(NotificationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"recipient\":\"test@example.com\",\"subject\":\"Test Subject\",\"message\":\"Test Message\",\"type\":\"EMAIL\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("test-id"))
                .andExpect(jsonPath("$.recipient").value("test@example.com"));
    }

    @Test
    void shouldGetNotificationById() throws Exception {
        NotificationResponse response = NotificationResponse.builder()
                .id("test-id")
                .recipient("test@example.com")
                .subject("Test Subject")
                .message("Test Message")
                .type(NotificationType.EMAIL)
                .build();

        when(notificationService.getNotificationById("test-id"))
                .thenReturn(Optional.of(response));

        mockMvc.perform(get("/api/notifications/test-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("test-id"));
    }

    @Test
    void shouldReturnNotFoundWhenNotificationDoesNotExist() throws Exception {
        when(notificationService.getNotificationById("non-existent"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/notifications/non-existent"))
                .andExpect(status().isNotFound());
    }

}
