package com.steve.hotel_booking_app.auth;


import com.steve.hotel_booking_app.common.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody @Valid AuthRegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        Response res = Response.builder()
                .status(200)
                .message("User registered Successfully")
                .build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody @Valid AuthLoginRequest authLoginRequest) {
        AuthLoginResponse authLoginResponse = authService.loginUser(authLoginRequest);
         Response response = Response.builder()
                 .status(200)
                 .message("User logged Successfully")
                 .role(authLoginResponse.getUserRole())
                 .token(authLoginResponse.getToken())
                 .isActive(authLoginResponse.isActive())
                 .expirationTime("6 months")
                 .build();
        return  ResponseEntity.ok(response);
    }
}
