package rgonzalez.notification.controller;

import rgonzalez.notification.dto.NotificationRequest;
import rgonzalez.notification.dto.NotificationResponse;
import rgonzalez.notification.model.NotificationType;
import rgonzalez.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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
                .content(
                        "{\"recipient\":\"test@example.com\",\"subject\":\"Test Subject\",\"message\":\"Test Message\",\"type\":\"EMAIL\"}"))
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
