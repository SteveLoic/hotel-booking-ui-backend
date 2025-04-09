package com.steve.hotel_booking_app.booking;


import com.steve.hotel_booking_app.common.PageResponse;
import com.steve.hotel_booking_app.common.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking")
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> fetchAllBooking(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1") int size
    ) {
        PageResponse<BookingResponse> allBooking = bookingService.fetchAllBooking(page, size);
        Response response = Response.builder()
                .status(200)
                .pageResponse(allBooking)
                .message("Retrieve all Booking successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/booking/{bookingReference}")
    public ResponseEntity<Response> fetchAllBooking(
            @PathVariable(name = "bookingReference") String bookingReference
    ) {
        BookingResponse foundBookingByReference = bookingService.findBookingByBookingReference(bookingReference);
        Response response = Response.builder()
                .status(200)
                .bookingResponse(foundBookingByReference)
                .message("Retrieve all Booking successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/booking/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CUSTOMER') ")
    public ResponseEntity<Response> createBooking(@RequestBody @Valid BookingRequest bookingRequest) {
        BookingResponse bookingResponse = bookingService.createBooking(bookingRequest);
        Response response = Response.builder()
                .status(200)
                .message("Booking created successfully")
                .bookingResponse(bookingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/booking/{bookingId}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateBooking(@RequestBody @Valid BookingRequest bookingRequest, @PathVariable(name = "bookingId") Long bookingId) {
        BookingResponse bookingResponse = bookingService.updateBooking(bookingRequest, bookingId);
        Response response = Response.builder()
                .status(200)
                .message("Booking has been updated successfully")
                .bookingResponse(bookingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteBookingById(@PathVariable(name = "bookingId") Long bookingId) {
        bookingService.deleteBooking(bookingId);
        Response response = Response.builder()
                .status(200)
                .message("Booking has been successfully deleted")
                .build();
        return ResponseEntity.ok(response);
    }
}
