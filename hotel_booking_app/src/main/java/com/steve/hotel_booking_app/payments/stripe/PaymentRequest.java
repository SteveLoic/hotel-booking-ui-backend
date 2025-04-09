package com.steve.hotel_booking_app.payments.stripe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {

    @NotNull(message = "Booking Reference can not be null")
    @NotBlank(message = "Booking Reference is required")
    private String bookingReference;

    private BigDecimal amount;

    private String transactionId;

    private boolean success;

    private String failureReason;

}
