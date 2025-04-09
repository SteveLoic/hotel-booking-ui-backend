package com.steve.hotel_booking_app.notificationTest;


import com.steve.hotel_booking_app.common.AbstracttionBaseTest;
import com.steve.hotel_booking_app.enums.NotificationType;
import com.steve.hotel_booking_app.notification.Notification;
import com.steve.hotel_booking_app.notification.NotificationRepository;
import com.steve.hotel_booking_app.notification.NotificationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class NotificatiionRepositoryTest extends AbstracttionBaseTest {

    @Autowired
    NotificationRepository notificationRepository;

    @Test
    void givenNotification_whenSave_ReturnNotification() {
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

        Notification savedNotification = notificationRepository.save(notification);


        Assertions.assertNotNull(savedNotification);
        Assertions.assertTrue(savedNotification.getId() > 0);
        Assertions.assertEquals(savedNotification.getNotificationType(), NotificationType.EMAIL);
    }


    @Test
    void givenNotificationList_whenSave_ReturnNotificationList() {

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .recipient("steve@example.com")
                .subject("Booking")
                .body("confirm booking of room 203")
                .build();

        List<Notification> notifications = Arrays.asList(
                Notification
                        .builder()
                        .recipient(notificationRequest.getRecipient())
                        .subject(notificationRequest.getSubject())
                        .body(notificationRequest.getBody())
                        .bookingReference(notificationRequest.getBookingReference())
                        .notificationType(NotificationType.EMAIL)
                        .build(),
                Notification
                        .builder()
                        .recipient(notificationRequest.getRecipient())
                        .subject(notificationRequest.getSubject())
                        .body(notificationRequest.getBody())
                        .bookingReference(notificationRequest.getBookingReference())
                        .notificationType(NotificationType.SMS)
                        .build(),
                Notification
                        .builder()
                        .recipient(notificationRequest.getRecipient())
                        .subject(notificationRequest.getSubject())
                        .body(notificationRequest.getBody())
                        .bookingReference(notificationRequest.getBookingReference())
                        .notificationType(NotificationType.WHATSAPP)
                        .build()
        );

        List<Notification> savedNotifications = notificationRepository.saveAllAndFlush(notifications);
        List<Notification> foundedNotifications = notificationRepository.findAll();

        Assertions.assertEquals(savedNotifications.size(), 3);
        Assertions.assertEquals(foundedNotifications.size(), 3);
    }

    @Test
    void givenNotification_whenUpdate_returnNotification() {
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

        Notification savedNotification = notificationRepository.save(notification);

        Assertions.assertNotNull(savedNotification);

        savedNotification.setNotificationType(NotificationType.SMS);

        Notification upadtedNotification = notificationRepository.save(notification);

        Assertions.assertEquals(upadtedNotification.getNotificationType(), NotificationType.SMS);
    }

    @Test
    void whenGivenSavedNotification_whenDelete_returnNotificationEmpty(){
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

        Notification savedNotification = notificationRepository.save(notification);

        Assertions.assertNotNull(savedNotification);

        notificationRepository.deleteById(savedNotification.getId());

        Optional<Notification> foundNotification = notificationRepository.findById(savedNotification.getId());

        Assertions.assertTrue(foundNotification.isEmpty());


    }
}
