package com.steve.hotel_booking_app.notificationTest;


import com.steve.hotel_booking_app.common.AbstracttionBaseTest;
import com.steve.hotel_booking_app.enums.NotificationType;
import com.steve.hotel_booking_app.notification.Notification;
import com.steve.hotel_booking_app.notification.NotificationRepository;
import com.steve.hotel_booking_app.notification.NotificationRequest;
import com.steve.hotel_booking_app.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest extends AbstracttionBaseTest {

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    NotificationRepository notificationRepository;


   @InjectMocks
    NotificationService notificationService;

    @Test
    public void givenNotificationTosend_WhenSend_SaveInDb() {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .recipient("steve@example.com")
                .subject("Booking")
                .body("confirm booking of room 203")
                .build();

        Notification notification = Notification
                .builder()
                .recipient(notificationRequest.getRecipient())
                .subject(notificationRequest.getSubject())
                .body(notificationRequest.getBody())
                .bookingReference(notificationRequest.getBookingReference())
                .notificationType(NotificationType.EMAIL)
                .build();


        notificationService.sendNotification(notificationRequest);


        Mockito.verify(javaMailSender,times(1)).send(any(SimpleMailMessage.class));
        Mockito.verify(notificationRepository, times(1)).save(any(Notification.class));

    }

}
