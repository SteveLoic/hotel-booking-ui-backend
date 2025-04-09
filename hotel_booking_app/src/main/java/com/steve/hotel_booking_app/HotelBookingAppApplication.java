package com.steve.hotel_booking_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HotelBookingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingAppApplication.class, args);
	}

}
