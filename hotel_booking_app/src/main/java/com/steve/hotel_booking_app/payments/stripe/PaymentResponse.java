package com.steve.hotel_booking_app.payments.stripe;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.steve.hotel_booking_app.auth.AuthLoginResponse;
import com.steve.hotel_booking_app.booking.BookingResponse;
import com.steve.hotel_booking_app.enums.PaymentGateway;
import com.steve.hotel_booking_app.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {

    private Long id;

    private String transactionId;

    private BigDecimal amount;

    private BookingResponse bookingResponse;

    private PaymentGateway paymentGateway;

    private LocalDateTime paymentDate;

    private PaymentStatus paymentStatus;

    private String  bookingReference;

    private  String failureReason;

    private AuthLoginResponse user;

    private String approvalLink;
}
