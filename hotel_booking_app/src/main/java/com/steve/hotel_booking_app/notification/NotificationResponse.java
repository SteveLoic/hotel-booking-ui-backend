package com.steve.hotel_booking_app.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.steve.hotel_booking_app.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {

    private Long id;
    private String subject;
    private String recipient;
    private String body;
    private NotificationType notificationType;
    private String bookingReference;
    private LocalDateTime createAt;
}
