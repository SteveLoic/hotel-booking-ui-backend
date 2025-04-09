package com.steve.hotel_booking_app.user;


import com.steve.hotel_booking_app.auth.AuthLoginResponse;
import com.steve.hotel_booking_app.auth.AuthRegisterRequest;
import com.steve.hotel_booking_app.booking.BookingResponse;
import com.steve.hotel_booking_app.common.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> fetchAllUser() {
        List<AuthLoginResponse> allUsers = userService.getAllUser();
        Response response = Response.builder()
                .status(200)
                .message("Retrieve all Users Successfully")
                .users(allUsers)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/account")
    public ResponseEntity<Response> getOwnAccountDetails() {
        AuthLoginResponse user = userService.getOwnAccountDetails();
        Response response = Response.builder()
                .status(200)
                .message("Retrieve User Account  info Successfully")
                .user(user)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/all/bookings")
    public ResponseEntity<Response> fetchAllBookingByCurrentUser() {
        List<BookingResponse>  allBookingResponsesByCurrent= userService.getBookingByUser();
        Response response = Response.builder()
                .status(200)
                .message("Retrieve all Booking by Current User")
                .bookingResponses(allBookingResponsesByCurrent)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/update")
    public ResponseEntity<Response> updateAccountDetails(@RequestBody AuthRegisterRequest registerRequest) {
        AuthLoginResponse user = userService.updateOwnAccount(registerRequest);
        Response response = Response.builder()
                .status(200)
                .message("User updated  Successfully")
                .user(user)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<Response> deleteOwnAccount(AuthRegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.deleteOwnAccount());
    }
}
