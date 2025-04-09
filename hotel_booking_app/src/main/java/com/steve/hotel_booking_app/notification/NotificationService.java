package com.steve.hotel_booking_app.notification;


import com.steve.hotel_booking_app.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    private final NotificationRepository notificationRepository;

    @Async
    public void sendNotification(NotificationRequest notificationRequest) {
        log.info("Enter im method sendNotification");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(notificationRequest.getRecipient());
        simpleMailMessage.setSubject(notificationRequest.getSubject());
        simpleMailMessage.setText(notificationRequest.getBody());
        javaMailSender.send(simpleMailMessage);

        Notification notification = Notification.builder()
                .recipient(notificationRequest.getRecipient())
                .subject(notificationRequest.getSubject())
                .body(notificationRequest.getBody())
                .bookingReference(notificationRequest.getBookingReference())
                .notificationType(NotificationType.EMAIL)
                .build();
       notificationRepository.save(notification);
    }

}
