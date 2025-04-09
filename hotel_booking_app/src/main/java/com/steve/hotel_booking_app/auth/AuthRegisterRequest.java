package com.steve.hotel_booking_app.auth;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.steve.hotel_booking_app.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRegisterRequest {

    @Email(message = "Email must be a valid")
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email can not be null")
    private String email;

    @NotBlank(message = "Password is required")
    @NotNull(message = "Password can not be null")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @NotBlank(message = "Firstname is required")
    @NotNull(message = "Firstname can not be null")
    @Size(min = 2, message = "Firstname must be at least 2 characters")
    private String firstname;

    @NotBlank(message = "LastName is required")
    @NotNull(message = "LastName can not be null")
    @Size(min = 2, message = "Firstname must be at least 2 characters")
    private String lastName;

    @NotBlank(message = "PhoneNumber is required")
    @Positive(message = "PhoneNumber must be positive")
    private String phoneNumber;

    private UserRole userRole;


}
