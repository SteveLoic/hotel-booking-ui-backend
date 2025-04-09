package com.steve.hotel_booking_app.user;


import com.steve.hotel_booking_app.auth.AuthLoginResponse;
import com.steve.hotel_booking_app.auth.AuthRegisterRequest;
import com.steve.hotel_booking_app.booking.Booking;
import com.steve.hotel_booking_app.booking.BookingRepository;
import com.steve.hotel_booking_app.booking.BookingResponse;
import com.steve.hotel_booking_app.common.Response;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BookingRepository bookingRepository;

    public List<AuthLoginResponse> getAllUser() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<AuthLoginResponse> authLoginResponseList = modelMapper.map(users, new TypeToken<List<AuthLoginResponse>>() {
        }.getType());
        return authLoginResponseList;
    }


    public AuthLoginResponse getOwnAccountDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("User is not found"))
        );
        AuthLoginResponse authLoginResponse = modelMapper.map(user, AuthLoginResponse.class);
        return authLoginResponse;
    }


    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("User is not found"))
        );
        return user;
    }

    public AuthLoginResponse updateOwnAccount(AuthRegisterRequest registerRequest) {
        User updatedUser = this.updateExistingUser(getCurrentUser(), registerRequest);
        User savedUser = userRepository.save(updatedUser);
        return modelMapper.map(savedUser, AuthLoginResponse.class);
    }

    public List<BookingResponse> getBookingByUser() {
        User currentUser = getCurrentUser();
        List<Booking> allBookingsByUser = bookingRepository.findByUserId(currentUser.getId());
        List<BookingResponse> allBookingResponsesByUser = modelMapper.map(allBookingsByUser, new TypeToken<List<BookingResponse>>() {
        }.getType());
        return allBookingResponsesByUser;
    }

    public Response deleteOwnAccount() {
        User userTobeDelete = this.getCurrentUser();
        userRepository.delete(userTobeDelete);
        return Response.builder()
                .status(200)
                .message("User deleted successfully")
                .build();
    }

    private User updateExistingUser(User existingsUser, AuthRegisterRequest registerRequest) {
        existingsUser.setEmail(Objects.equals(registerRequest.getEmail(), null) ? existingsUser.getEmail() : registerRequest.getEmail());
        existingsUser.setFirstname(Objects.equals(registerRequest.getFirstname(), null) ? existingsUser.getFirstname() : registerRequest.getFirstname());
        existingsUser.setLastName(Objects.equals(registerRequest.getLastName(), null) ? existingsUser.getLastName() : registerRequest.getLastName());
        existingsUser.setPassword(Objects.equals(registerRequest.getPassword(), null) ? existingsUser.getPassword() : registerRequest.getPassword());
        existingsUser.setPhoneNumber(Objects.equals(registerRequest.getPhoneNumber(), null) ? existingsUser.getPhoneNumber() : registerRequest.getPhoneNumber());
        return existingsUser;
    }

}
