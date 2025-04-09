package com.steve.hotel_booking_app.booking;


import com.steve.hotel_booking_app.bookingrefence.BookingCodeGenerator;
import com.steve.hotel_booking_app.common.PageResponse;
import com.steve.hotel_booking_app.enums.BookingStatus;
import com.steve.hotel_booking_app.enums.PaymentStatus;
import com.steve.hotel_booking_app.exceptions.InvalidBookingStateAndDateException;
import com.steve.hotel_booking_app.exceptions.InvalidCredentialException;
import com.steve.hotel_booking_app.notification.NotificationRequest;
import com.steve.hotel_booking_app.notification.NotificationService;
import com.steve.hotel_booking_app.room.Room;
import com.steve.hotel_booking_app.room.RoomRepository;
import com.steve.hotel_booking_app.user.User;
import com.steve.hotel_booking_app.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final NotificationService notificationService;
    private final UserService userService;
    private final BookingCodeGenerator bookingCodeGenerator;
    private final ModelMapper modelMapper;

    public PageResponse<BookingResponse> fetchAllBooking(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> allBookings = bookingRepository.findAll(pageable);
        List<BookingResponse> allBookingsResponse = modelMapper.map(allBookings.get().toList(), new TypeToken<List<BookingResponse>>() {
        }.getType());

        allBookingsResponse.forEach(bookingItem -> {
            bookingItem.setUser(null);
            bookingItem.setRoom(null);
        });

        return new PageResponse<>(
                allBookingsResponse,
                allBookings.getNumber(),
                allBookings.getSize(),
                allBookings.getTotalElements(),
                allBookings.getTotalPages()
        );
    }

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        User currentUser = userService.getCurrentUser();
        Room room = roomRepository.findById(bookingRequest.getRoom().getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("room with %d not found", bookingRequest.getRoom().getId()))
        );
        if (bookingRequest.getCheckedInDate().isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check in Date can not before today");
        }
        if (bookingRequest.getCheckedOutDate().isBefore(bookingRequest.getCheckedInDate())) {
            throw new InvalidBookingStateAndDateException("Check out Date must be before check in date");
        }
        if (bookingRequest.getCheckedInDate().equals(bookingRequest.getCheckedOutDate())) {
            throw new InvalidBookingStateAndDateException("Check in Date  can not be equal check in date");
        }

        boolean isRoomAvailable = bookingRepository.isRoomAvailable(room.getId(), bookingRequest.getCheckedInDate(), bookingRequest.getCheckedOutDate());
        if (!isRoomAvailable) {
            throw new InvalidCredentialException("Room is not available to be booked");
        }

        BigDecimal totalPrice = calculateTotalPrice(room, bookingRequest);
        String bookingReference = bookingCodeGenerator.generateBookingReference();

        Booking booking = Booking.builder()
                .user(currentUser)
                .room(room)
                .checkedInDate(bookingRequest.getCheckedInDate())
                .checkedOutDate(bookingRequest.getCheckedOutDate())
                .totalPrice(bookingRequest.getTotalPrice())
                .bookingReference(bookingReference)
                .paymentStatus(PaymentStatus.PENDING)
                .bookingStatus(BookingStatus.BOOKED)
                .createdAt(LocalDateTime.now())
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        String paymentLink = "http:/localhost:4200/payment/" + bookingReference + "/" + totalPrice;
        log.info("Booking Succesfully Link is {} ", paymentLink);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .recipient(currentUser.getEmail())
                .subject("Booking Confirmation")
                .body(String.format("Your Booking has been created successfully. \n Please procees with Payment using the payment like below %s,", paymentLink))
                .bookingReference(savedBooking.getBookingReference())
                .build();
        notificationService.sendNotification(notificationRequest);

        return modelMapper.map(savedBooking, BookingResponse.class);
    }

    public BookingResponse findBookingByBookingReference(String bookingReference) {
        Booking foundBookingByReference = bookingRepository.findByBookingReference(bookingReference).orElseThrow(
                () -> new EntityNotFoundException(String.format("Booking reference with %s id not found", bookingReference))
        );
        return modelMapper.map(foundBookingByReference, BookingResponse.class);
    }

    BookingResponse updateBooking(BookingRequest bookingRequest, Long bookingId) {
        Booking updatedBooking = bookingRepository.findById(bookingId)
                .map(existingsBooking -> updateExistingsBooking(existingsBooking, bookingRequest))
                .map(bookingRepository::save)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Booking with %d not found", bookingId)));
        return modelMapper.map(updatedBooking, BookingResponse.class);
    }

    public void deleteBooking(Long bookingId) {
        bookingRepository.findById(bookingId).ifPresentOrElse(
                bookingRepository::delete, () -> {
                    throw new EntityNotFoundException(String.format("Booking with %d not found", bookingId));
                }
        );
    }


    private BigDecimal calculateTotalPrice(Room room, BookingRequest bookingRequest) {
        BigDecimal pricePerNight = room.getPricePerNight();
        long days = ChronoUnit.DAYS.between(bookingRequest.getCheckedInDate(), bookingRequest.getCheckedOutDate());
        return pricePerNight.multiply(BigDecimal.valueOf(days));
    }


    private Booking updateExistingsBooking(Booking existingsBooking, BookingRequest bookingRequest) {
        existingsBooking.setBookingStatus(Objects.equals(bookingRequest.getBookingStatus(), null) ? existingsBooking.getBookingStatus() : bookingRequest.getBookingStatus());
        existingsBooking.setPaymentStatus(Objects.equals(bookingRequest.getPaymentStatus(), null) ? existingsBooking.getPaymentStatus() : bookingRequest.getPaymentStatus());
        return existingsBooking;
    }


}
