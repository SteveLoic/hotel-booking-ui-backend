package com.steve.hotel_booking_app;

import com.steve.hotel_booking_app.enums.RoomType;
import com.steve.hotel_booking_app.enums.UserRole;
import com.steve.hotel_booking_app.room.Room;
import com.steve.hotel_booking_app.room.RoomRepository;
import com.steve.hotel_booking_app.user.User;
import com.steve.hotel_booking_app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
@EnableAsync
public class HotelBookingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingAppApplication.class, args);
    }

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {

            if (userRepository.count() == 0) {
                User[] users = {
                        User.builder()
                                .email("john.smith@test.com")
                                .phoneNumber("(568) 882-3584")
                                .password( passwordEncoder.encode("password1234"))
                                .firstname("john")
                                .lastName("smith")
                                .isActive(true)
                                .userRole(UserRole.ADMIN)
                                .build(),
                        User.builder()
                                .email("heidi.horton@test.com")
                                .phoneNumber("(568) 882-3585")
                                .password( passwordEncoder.encode("password1234"))
                                .firstname("Heidi")
                                .lastName("Horton")
                                .isActive(true)
                                .userRole(UserRole.CUSTOMER)
                                .build(),
                        User.builder()
                                .email("john.doe@test.com")
                                .phoneNumber("(568) 882-3586")
                                .password(passwordEncoder.encode("password1234"))
                                .firstname("john")
                                .lastName("doe")
                                .isActive(true)
                                .userRole(UserRole.CUSTOMER)
                                .build(),

                };
                userRepository.saveAll(Arrays.asList(users));
            }

            if (roomRepository.count() == 0) {
                Room[] rooms = {
                        Room.builder()
                                .capacity(2)
                                .description("Nice Lovely Room with Balcony")
                                .roomNumber(1)
                                .roomType(RoomType.SINGLE)
                                .pricePerNight(BigDecimal.valueOf(40))
                                .imageUrl("room-single.jpg")
                                .build(),
                        Room.builder()
                                .capacity(4)
                                .description("Nice Lovely Room with view on the mountain")
                                .roomNumber(2)
                                .roomType(RoomType.DOUBLE)
                                .pricePerNight(BigDecimal.valueOf(80))
                                .imageUrl("room-double.jpg")
                                .build(),
                        Room.builder()
                                .capacity(6)
                                .description("Nice Lovely Big Room with view on the mountain and Balcony")
                                .roomNumber(3)
                                .roomType(RoomType.TRIPLE)
                                .pricePerNight(BigDecimal.valueOf(120))
                                .imageUrl("room-triple.jpg")
                                .build(),
                        Room.builder()
                                .capacity(6)
                                .description("Nice Lovely Big Suit Room with view on the mountain and Balcony and Service")
                                .roomNumber(4)
                                .roomType(RoomType.SUIT)
                                .pricePerNight(BigDecimal.valueOf(200))
                                .imageUrl("room-suite.jpg")
                                .build(),
                        Room.builder()
                                .capacity(2)
                                .description("Nice Lovely Room with Balcony with nice badroom")
                                .roomNumber(5)
                                .roomType(RoomType.SINGLE)
                                .pricePerNight(BigDecimal.valueOf(45))
                                .imageUrl("room-single.jpg")
                                .build()
                };
                roomRepository.saveAll(Arrays.asList(rooms));
            }
        };
    }


}
