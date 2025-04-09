package com.steve.hotel_booking_app.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.steve.hotel_booking_app.auth.AuthLoginResponse;
import com.steve.hotel_booking_app.enums.BookingStatus;
import com.steve.hotel_booking_app.enums.PaymentStatus;
import com.steve.hotel_booking_app.room.RoomResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequest {

    private AuthLoginResponse user;
    private RoomResponse room;
    private PaymentStatus paymentStatus;
    private LocalDate checkedInDate;
    private LocalDate checkedOutDate;
    private BigDecimal totalPrice;
    private String bookingReference;
    private BookingStatus bookingStatus;

}
