package com.steve.hotel_booking_app.common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract  class  AbstracttionBaseTest {
    @Container
    private static MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest")
                .withUsername("username")
                .withPassword("password")
                .withDatabaseName("hotelbooking");
    }

    @BeforeAll
    public  static void  beforeAll() {
       MY_SQL_CONTAINER.start();
    }

    @AfterAll
    public  static  void afterAll() {
        MY_SQL_CONTAINER.stop();
    }

    @Test()
    void isRunning() {
        Assertions.assertTrue(MY_SQL_CONTAINER.isRunning());
    }

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }
}

