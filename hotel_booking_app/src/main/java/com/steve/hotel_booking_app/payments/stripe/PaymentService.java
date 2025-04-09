package com.steve.hotel_booking_app.payments.stripe;


import com.steve.hotel_booking_app.booking.Booking;
import com.steve.hotel_booking_app.booking.BookingRepository;
import com.steve.hotel_booking_app.enums.NotificationType;
import com.steve.hotel_booking_app.enums.PaymentGateway;
import com.steve.hotel_booking_app.enums.PaymentStatus;
import com.steve.hotel_booking_app.exceptions.PaymentAlreadyCompleted;
import com.steve.hotel_booking_app.notification.NotificationRequest;
import com.steve.hotel_booking_app.notification.NotificationService;
import com.steve.hotel_booking_app.payments.PaymentRepository;
import com.steve.hotel_booking_app.user.User;
import com.steve.hotel_booking_app.user.UserService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final BookingRepository bookingRepository;

    private final NotificationService emailService;

    private final UserService userService;

    @Value("${stripe.api.secret.key}")
    private String secretKey;

    public PaymentResponse createPaymentIntent(PaymentRequest paymentRequest) {
        Stripe.apiKey = secretKey;

        String bookingReference = paymentRequest.getBookingReference();

        Booking foundBooking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("booking reference not found %s", paymentRequest.getBookingReference())
                ));
        if (foundBooking.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new PaymentAlreadyCompleted(String.format("Payment already made for %s", bookingReference));
        }

        if (foundBooking.getTotalPrice().compareTo(paymentRequest.getAmount()) != 0) {
            throw new EntityNotFoundException("Payment Amout does not Tally. Please Contact Out Customer Support Agent");
        }
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams
                    .builder()
                    .setAmount(paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                    .setCurrency("euro")
                    .putMetadata("bookingReference", bookingReference)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            String uniqueTransactionId = paymentIntent.getClientSecret();
            return PaymentResponse.builder().transactionId(uniqueTransactionId).build();
        } catch (Exception ex) {
            throw new RuntimeException("Error Creatig payment unique transactionId");
        }

    }

    public void updatePaymentBooking(PaymentRequest paymentRequest) {
        String bookingReference = paymentRequest.getBookingReference();
        User currentUser = userService.getCurrentUser();

        Booking foundBooking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("booking reference not found %s", paymentRequest.getBookingReference())
                ));

        Payment payment = Payment.builder()
                .paymentGateway(PaymentGateway.STRIPE)
                .amount(paymentRequest.getAmount())
                .transactionId(paymentRequest.getTransactionId())
                .paymentStatus(paymentRequest.isSuccess() ? PaymentStatus.COMPLETED : PaymentStatus.Failed)
                .paymentDate(LocalDateTime.now())
                .bookingReference(paymentRequest.getBookingReference())
                .user(currentUser)
                .build();
        if (!paymentRequest.isSuccess()) {
            payment.setFailureReason(payment.getFailureReason());
        }

        paymentRepository.save(payment);

        NotificationRequest notifrequest = NotificationRequest.builder()
                .recipient(foundBooking.getUser().getEmail())
                .notificationType(NotificationType.EMAIL)
                .bookingReference(bookingReference)
                .build();

        if (paymentRequest.isSuccess()) {
            foundBooking.setPaymentStatus(PaymentStatus.COMPLETED);
            bookingRepository.save(foundBooking);
            notifrequest.setSubject("BOOKING PAYMENT SUCCESSFULL");
            notifrequest.setBody("Congratulations for the booking. Your Payment for booking with reference:" + bookingReference + " is successfull.Happy to see you soon");
            emailService.sendNotification(notifrequest);
        } else {
            foundBooking.setPaymentStatus(PaymentStatus.Failed);
            bookingRepository.save(foundBooking);
            notifrequest.setSubject("BOOKING PAYMENT Failed");
            notifrequest.setBody("Congratulations for the booking. Your Payment for booking with reference:" + bookingReference + " is Failed.Happy to see you soon");
            emailService.sendNotification(notifrequest);
        }

    }

}
