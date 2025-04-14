package com.steve.hotel_booking_app.room;

import com.steve.hotel_booking_app.common.PageResponse;
import com.steve.hotel_booking_app.enums.RoomType;
import com.steve.hotel_booking_app.exceptions.InvalidBookingStateAndDateException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    RoomResponse addRoom(RoomRequest roomRequest) {
        Room room = Optional.of(roomRequest)
                .filter(request -> !roomRepository.existsByRoomNumber(roomRequest.getRoomNumber()))
                .map(request -> modelMapper.map(request, Room.class))
                .map(roomRepository::save).orElseThrow(
                        () -> new EntityExistsException(String.format("room with number %d already exists", roomRequest.getRoomNumber()))
                );
        return modelMapper.map(room, RoomResponse.class);
    }

    RoomResponse updateRoom(RoomRequest roomRequest, Long roomId) {
        Room updatedRoom = roomRepository.findById(roomId)
                .map(existingsRoom -> updateExistingsRoom(existingsRoom, roomRequest))
                .map(roomRepository::save).orElseThrow(
                        () -> new EntityNotFoundException(String.format("room with number %d not found", roomRequest.getRoomNumber()))
                );
        return modelMapper.map(updatedRoom, RoomResponse.class);
    }


    RoomResponse getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new EntityNotFoundException(String.format("room with Id %d not found", roomId))
        );
        return modelMapper.map(room, RoomResponse.class);
    }

    void deleteRoom(Long roomId) {
        roomRepository.findById(roomId).ifPresentOrElse(
                roomRepository::delete, () -> {
                    throw new EntityNotFoundException(String.format("room with Id %d not found", roomId));
                }
        );
    }

    PageResponse<RoomResponse> getAllRooms(int page, int size) {
        Pageable pageOfRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"id"));
        Page<Room> roomResponses = roomRepository.findAll(pageOfRequest);
        List<RoomResponse> allRooms = modelMapper.map(roomResponses.get().toList(), new TypeToken<List<RoomResponse>>() {
        }.getType());
        return new PageResponse<>(
                allRooms,
                roomResponses.getNumber(),
                roomResponses.getSize(),
                roomResponses.getTotalElements(),
                roomResponses.getTotalPages()
        );
    }

     PageResponse<RoomResponse> getAvailableRooms(LocalDate checkinDate, LocalDate checkoutDate, RoomType roomType, int page, int size) {
        if (checkinDate.isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check in Date can not before today");
        }
        if (checkoutDate.isBefore(checkinDate)) {
            throw new InvalidBookingStateAndDateException("Check out Date must be before check in date");
        }

        if (checkinDate.equals(checkoutDate)) {
            throw new InvalidBookingStateAndDateException("Check in Date  can not be equal check in date");
        }
        Pageable pageRequest = PageRequest.of(page, size);
        //List<Room> availableRooms = roomRepository.findAvailableRooms(pageRequest,checkinDate, checkoutDate, roomType);
        Page<Room> availableRooms =  roomRepository.findAvailableRooms(pageRequest,checkinDate, checkoutDate, roomType);
        List<RoomResponse> availableRoomsResponse = modelMapper.map(availableRooms.get().toList(), new TypeToken<List<RoomResponse>>() {
        }.getType());
        return new PageResponse<>(
                availableRoomsResponse,
                availableRooms.getNumber(),
                availableRooms.getSize(),
                availableRooms.getTotalElements(),
                availableRooms.getTotalPages()
        );
    }

    List<RoomType> getAllRoomTypes() {
        return Arrays.asList(RoomType.values());
    }

    PageResponse<RoomResponse> searchRooms(String search, int page, int size) {

        Pageable pageOfRequest = PageRequest.of(page, size);
        Page<Room> foundedRooms = roomRepository.searchRooms(pageOfRequest, search);
        List<RoomResponse> foundedRoomsResponse = modelMapper.map(foundedRooms, new TypeToken<List<RoomResponse>>() {
        }.getType());
        return new PageResponse<>(
                foundedRoomsResponse,
                foundedRooms.getNumber(),
                foundedRooms.getSize(),
                foundedRooms.getTotalElements(),
                foundedRooms.getTotalPages()
        );
    }

    private Room updateExistingsRoom(Room existingRooom, RoomRequest roomRequest) {
        existingRooom.setRoomType(Objects.equals(roomRequest.getRoomType(), null) ? existingRooom.getRoomType() : roomRequest.getRoomType());
        existingRooom.setDescription(Objects.equals(roomRequest.getDescription(), null) ? existingRooom.getDescription() : roomRequest.getDescription());
        existingRooom.setPricePerNight(Objects.equals(roomRequest.getPricePerNight(), null) ? existingRooom.getPricePerNight() : roomRequest.getPricePerNight());
        existingRooom.setCapacity(Objects.equals(roomRequest.getCapacity(), null) ? existingRooom.getCapacity() : roomRequest.getCapacity());
        existingRooom.setRoomNumber(Objects.equals(roomRequest.getRoomNumber(), null) ? existingRooom.getRoomNumber() : roomRequest.getRoomNumber());
        existingRooom.setDescription(Objects.equals(roomRequest.getDescription(), null) ? existingRooom.getDescription() : roomRequest.getDescription());
        return existingRooom;
    }
}
