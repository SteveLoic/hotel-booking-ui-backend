package com.steve.hotel_booking_app.room;


import com.steve.hotel_booking_app.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(Integer roomNumber);

    Optional<Room> findRoomByRoomNumber(Integer roomNumber);

    List<Room> findAllByRoomType(RoomType roomType);

    @Query("""
            SELECT r FROM Room r
            WHERE
                r.id NOT IN (
                    SELECT b.room.id
                    FROM Booking b
                    WHERE :checkInDate <= b.checkedOutDate
                    AND :checkOutDate >= b.checkedInDate
                    AND b.bookingStatus IN ('BOOKED', 'CHECKED_IN')
                )
                AND (:roomType IS NULL OR r.roomType = :roomType)
            """)
    Page<Room> findAvailableRooms(
            Pageable pageable,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("roomType") RoomType roomType);

    @Query("""
                SELECT r FROM Room r
                WHERE CAST(r.roomNumber AS string) LIKE %:searchParam%
                   OR LOWER(r.roomType) LIKE LOWER(:searchParam)
                   OR CAST(r.pricePerNight AS string) LIKE %:searchParam%
                   OR CAST(r.capacity AS string) LIKE %:searchParam%
                   OR LOWER(r.description) LIKE LOWER(CONCAT('%', :searchParam, '%'))
            """)
    Page<Room> searchRooms(Pageable pageable, @Param("searchParam") String searchParam);
}
