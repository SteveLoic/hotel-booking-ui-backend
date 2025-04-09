package com.steve.hotel_booking_app.exceptions;

public class PaymentAlreadyCompleted extends RuntimeException {
    public PaymentAlreadyCompleted(String message) {
        super(message);
    }
}
