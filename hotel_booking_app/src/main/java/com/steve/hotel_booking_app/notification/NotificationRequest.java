package com.steve.hotel_booking_app.notification;

import com.steve.hotel_booking_app.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    private String subject;
    @NotNull(message = "recipient can not be null")
    @NotBlank(message = "recipient is required")
    private String recipient;
    private String body;
    private NotificationType notificationType;
    private String bookingReference;

}
