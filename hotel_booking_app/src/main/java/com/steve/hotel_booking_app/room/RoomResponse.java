package com.steve.hotel_booking_app.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.steve.hotel_booking_app.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponse {
    private Long id;
    private Integer roomNumber;

    private RoomType roomType;


    private BigDecimal pricePerNight;


    private Integer capacity;


    private  String description;

    private String imageUrl;
}
