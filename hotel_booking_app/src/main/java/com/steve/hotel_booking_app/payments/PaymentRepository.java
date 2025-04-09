package com.steve.hotel_booking_app.payments;

import com.steve.hotel_booking_app.payments.stripe.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
