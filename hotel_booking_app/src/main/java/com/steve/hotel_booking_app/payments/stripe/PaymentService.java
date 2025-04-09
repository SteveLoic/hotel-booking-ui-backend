package com.steve.hotel_booking_app.payments.stripe;


import com.steve.hotel_booking_app.booking.Booking;
import com.steve.hotel_booking_app.booking.BookingRepository;
import com.steve.hotel_booking_app.enums.PaymentStatus;
import com.steve.hotel_booking_app.exceptions.PaymentAlreadyCompleted;
import com.steve.hotel_booking_app.notification.NotificationService;
import com.steve.hotel_booking_app.payments.PaymentRepository;
import com.stripe.Stripe;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final BookingRepository bookingRepository;

    private final NotificationService emailService;

    @Value("${stripe.api.secret.key}")
    private String secretKey;

    public PaymentResponse createPaymentIntent(PaymentRequest paymentRequest) {
        Stripe.apiKey = secretKey;

        String bookingReference = paymentRequest.getBookingReference();

        Booking foundBooking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("booking reference not found %s", paymentRequest.getBookingReference())
                ));
        if(foundBooking.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw  new PaymentAlreadyCompleted(String.format(""));
        }

    }

}
