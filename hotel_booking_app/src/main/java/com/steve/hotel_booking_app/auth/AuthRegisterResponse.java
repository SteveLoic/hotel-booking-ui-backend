package com.steve.hotel_booking_app.auth;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.steve.hotel_booking_app.enums.UserRole;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRegisterResponse {

    private Long id;
    private String email;

    @JsonIgnore
    private String password;

    private String firstname;

    private String lastName;

    private String phoneNumber;

    private UserRole userRole;

    private boolean isActive;
}
