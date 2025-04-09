package com.steve.hotel_booking_app.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthLoginRequest {

    @Email(message = "Email must be a valid")
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email can not be null")
    private String email;

    @NotBlank(message = "Password is required")
    @NotNull(message = "Password can not be null")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

}
