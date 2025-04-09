package com.steve.hotel_booking_app.auth;


import com.steve.hotel_booking_app.enums.UserRole;
import com.steve.hotel_booking_app.exceptions.InvalidCredentialException;
import com.steve.hotel_booking_app.security.JwtUtils;
import com.steve.hotel_booking_app.user.User;
import com.steve.hotel_booking_app.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final JwtUtils jwtUtils;

    public void registerUser(AuthRegisterRequest registerRequest) {

        User user = Optional.of(registerRequest)
                .filter(request -> !userRepository.existsByEmail(registerRequest.getEmail()))
                .map(request -> {
                    UserRole role = UserRole.CUSTOMER;
                    if (!Objects.equals(request.getUserRole(), null)) {
                        role = request.getUserRole();
                    }
                    return User.builder()
                            .firstname(request.getFirstname())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .phoneNumber(request.getPhoneNumber())
                            .userRole(role)
                            .isActive(true)
                            .build();
                }).map(
                        userRepository::save
                ).orElseThrow(() -> new EntityExistsException(String.format("User with email %s already exists", registerRequest.getEmail())));

    }

    public AuthLoginResponse loginUser(AuthLoginRequest authLoginRequest) {
        User user = userRepository.findByEmail(authLoginRequest.getEmail()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with email %s not exists", authLoginRequest.getEmail()))
        );

        if (!passwordEncoder.matches(authLoginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException(String.format("Invalid Password"));
        }
        String token = jwtUtils.generateToken(user.getEmail());
        AuthLoginResponse authLoginResponse = modelMapper.map(user, AuthLoginResponse.class);
        authLoginResponse.setToken(token);
        return authLoginResponse;
    }

}
