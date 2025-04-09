package com.steve.hotel_booking_app.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.steve.hotel_booking_app.auth.AuthLoginResponse;
import com.steve.hotel_booking_app.booking.BookingResponse;
import com.steve.hotel_booking_app.enums.RoomType;
import com.steve.hotel_booking_app.enums.UserRole;
import com.steve.hotel_booking_app.notification.NotificationResponse;
import com.steve.hotel_booking_app.payments.stripe.PaymentResponse;
import com.steve.hotel_booking_app.room.RoomResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int status;
    private String message;


    private String token;
    private UserRole role;
    private Boolean isActive;
    private  String expirationTime;

    private AuthLoginResponse user;
    private List<AuthLoginResponse> users;


    private BookingResponse bookingResponse;
    private List<BookingResponse> bookingResponses;


    private RoomResponse roomResponse;
    private List<RoomResponse> roomResponses;

    private String transactionId;
    private PaymentResponse paymentResponse;
    private List<PaymentResponse> paymentResponses;

    private NotificationResponse notificationResponse;
    private List<NotificationResponse>notificationResponses;

    private List<RoomType> roomTypes;

    private PageResponse pageResponse;

    private  final LocalDateTime timeStamp = LocalDateTime.now();
}
