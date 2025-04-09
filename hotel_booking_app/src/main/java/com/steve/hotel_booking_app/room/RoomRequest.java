package com.steve.hotel_booking_app.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.steve.hotel_booking_app.enums.RoomType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomRequest {

    @Positive(message = "Room number must be positive")
    @Min(value = 1, message = "Room Number must be at least 1")
    private Integer roomNumber;

    private RoomType roomType;

    @DecimalMin(value = "0.1", message = "price per Nigth is required")
    @Positive(message = "Price  must be positive number")
    private BigDecimal pricePerNight;

    @Positive(message = "Room capacity must be positive number")
    @Min(value = 1, message = "Room capacity must be at least 1")
    private Integer capacity;

    @NotBlank(message = "description can not be null")
    @NotNull(message = "LastName can not be null")
    private  String description;

    private String imageUrl;
}
