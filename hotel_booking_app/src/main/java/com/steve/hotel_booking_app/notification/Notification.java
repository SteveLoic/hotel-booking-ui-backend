package com.steve.hotel_booking_app.notification;


import com.steve.hotel_booking_app.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Column(nullable = false)
    private String recipient;

    private String body;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String bookingReference;

    @Column(updatable = false)
    private  LocalDateTime createAt;
}
