package com.steve.hotel_booking_app.payments.stripe;

import com.steve.hotel_booking_app.enums.PaymentGateway;
import com.steve.hotel_booking_app.enums.PaymentStatus;
import com.steve.hotel_booking_app.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentGateway paymentGateway;

    private LocalDateTime paymentDate;

    private PaymentStatus  paymentStatus;

    private String  bookingReference;

    private  String failureReason;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
