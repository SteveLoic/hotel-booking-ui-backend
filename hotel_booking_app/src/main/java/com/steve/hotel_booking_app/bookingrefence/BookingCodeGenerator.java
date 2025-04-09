package com.steve.hotel_booking_app.bookingrefence;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingCodeGenerator {
    private final BookingReferenceRepository bookingReferenceRepository;

    public String generateBookingReference() {
        String bookingReference;
        do {
            bookingReference = generateRandomAlphanumericCode(10);
        } while (isBookingReferenceExists(bookingReference));
        saveBookingReference(bookingReference);
        return bookingReference;
    }

    private String generateRandomAlphanumericCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }
        return stringBuilder.toString();
    }

    private boolean isBookingReferenceExists(String bookingReference) {
        return bookingReferenceRepository.findByReferenceNo(bookingReference).isPresent();
    }

    private void saveBookingReference(String bookingReference) {
        BookingReference bookingReferenceToBeSave = BookingReference.builder()
                .referenceNo(bookingReference)
                .build();
        bookingReferenceRepository.save(bookingReferenceToBeSave);
    }
}
