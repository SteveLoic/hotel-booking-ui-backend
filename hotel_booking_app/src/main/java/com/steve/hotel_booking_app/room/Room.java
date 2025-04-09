package com.steve.hotel_booking_app.room;


import com.steve.hotel_booking_app.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    @Min(value = 1, message = "Room Number must be at least 1")
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @DecimalMin(value = "0.1", message = "price per Nigth is required")
    private BigDecimal pricePerNight;

    @Min(value = 1, message = "Room Number must be at least 1")
    private Integer capacity;

    @NotBlank(message = "description can not be null")
    private  String description;

    private String imageUrl;

}
