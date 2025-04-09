package com.steve.hotel_booking_app.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.steve.hotel_booking_app.auth.AuthLoginResponse;
import com.steve.hotel_booking_app.enums.PaymentStatus;
import com.steve.hotel_booking_app.room.RoomResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {

    private AuthLoginResponse user;
    private RoomResponse room;
    private PaymentStatus paymentStatus;
    private LocalDateTime checkedInDate;
    private LocalDateTime checkedOutDate;
    private BigDecimal totalPrice;
    private String bookingReference;

}
