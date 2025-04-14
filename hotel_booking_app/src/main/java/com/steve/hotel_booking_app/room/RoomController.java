package com.steve.hotel_booking_app.room;


import com.steve.hotel_booking_app.common.PageResponse;
import com.steve.hotel_booking_app.common.Response;
import com.steve.hotel_booking_app.enums.RoomType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "Rooms")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/all/rooms/{searchParam}")
    public ResponseEntity<Response> searchRooms(
            @PathVariable(name = "searchParam") String searchParam,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        PageResponse<RoomResponse> foundedRooms = roomService.searchRooms(searchParam, page, size);
        Response response = Response.builder()
                .status(200)
                .message("Retrieve all Room Successfully")
                .pageResponse(foundedRooms)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRoom(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        PageResponse<RoomResponse> allRooms = roomService.getAllRooms(page, size);
        Response response = Response.builder()
                .status(200)
                .message("Retrieve all Room Successfully")
                .pageResponse(allRooms)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/available/rooms")
    public ResponseEntity<Response> getAllAvailableRooms(
            @RequestParam(name = "checkinDate") LocalDate checkinDate,
            @RequestParam(name = "checkoutDate") LocalDate checkoutDate,
            @RequestParam(name = "roomType", required = false) RoomType roomType,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        PageResponse<RoomResponse> availableRooms = roomService.getAvailableRooms(checkinDate, checkoutDate, roomType, page, size);
        Response response = Response.builder()
                .status(200)
                .message("Retrieve all available Rooms Successfully")
                .pageResponse(availableRooms)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Response> updateRoom(@PathVariable(value = "roomId") Long roomId) {
        RoomResponse getRoomById = roomService.getRoomById(roomId);
        Response response = Response.builder()
                .status(200)
                .message("Retrieve room successfully")
                .roomResponse(getRoomById)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/types")
    public ResponseEntity<Response> getAllRoomsTypes(
    ) {
        List<RoomType> allRoomsTypes = roomService.getAllRoomTypes();
        Response response = Response.builder()
                .status(200)
                .message("Retrieve all Room Type Successfully")
                .roomTypes(allRoomsTypes)
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/room/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addRoom(@RequestBody @Valid RoomRequest roomRequest) {
        RoomResponse addedRoom = roomService.addRoom(roomRequest);
        Response response = Response.builder()
                .status(200)
                .message("Room successfully Added")
                .roomResponse(addedRoom)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/room/{roomId}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@RequestBody RoomRequest roomRequest, @PathVariable(name = "roomId") Long roomId) {
        RoomResponse updatedRoom = roomService.updateRoom(roomRequest, roomId);
        Response response = Response.builder()
                .status(200)
                .message("Room successfully updated")
                .roomResponse(updatedRoom)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/room/{roomId}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable(value = "roomId") Long roomId) {
        roomService.deleteRoom(roomId);
        Response response = Response.builder()
                .status(200)
                .message("room has been successfully deleted")
                .build();
        return ResponseEntity.ok(response);
    }

}
