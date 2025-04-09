package com.steve.hotel_booking_app.room;

import com.steve.hotel_booking_app.enums.RoomType;
import com.steve.hotel_booking_app.exceptions.InvalidBookingStateAndDateException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    List<RoomResponse> getAllRooms() {
        List<Room> roomResponses = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<RoomResponse> allRooms = modelMapper.map(roomResponses, new TypeToken<List<RoomResponse>>() {
        }.getType());
        return allRooms;
    }

    List<RoomResponse> getAvailableRooms(LocalDate checkinDate, LocalDate checkoutDate, RoomType roomType) {
        if (checkinDate.isBefore(LocalDate.now())) {
            throw new InvalidBookingStateAndDateException("Check in Date can not before today");
        }
        if (checkoutDate.isBefore(checkinDate)) {
            throw new InvalidBookingStateAndDateException("Check out Date must be before check in date");
        }

        if (checkinDate.equals(checkoutDate)) {
            throw new InvalidBookingStateAndDateException("Check in Date  can not be equal check in date");
        }

        List<Room> availableRooms = roomRepository.findAvailableRooms(checkinDate, checkoutDate, roomType);
        List<RoomResponse> availableRoomsResponse = modelMapper.map(availableRooms, new TypeToken<List<RoomResponse>>() {
        }.getType());
        return availableRoomsResponse;
    }

    List<RoomType> getAllRoomTypes() {
        return Arrays.asList(RoomType.values());
    }

    List<RoomResponse> searchRooms(String search) {
        List<Room> foundedRooms = roomRepository.searchRooms(search);
        List<RoomResponse> foundedRoomsResponse = modelMapper.map(foundedRooms, new TypeToken<List<RoomResponse>>() {
        }.getType());
        return foundedRoomsResponse;
    }

    private Room updateExistingsRoom(Room existingRooom, RoomRequest roomRequest) {
        existingRooom.setRoomNumber(Objects.equals(roomRequest.getRoomNumber(), null) ? existingRooom.getRoomNumber() : roomRequest.getRoomNumber());
        existingRooom.setDescription(Objects.equals(roomRequest.getDescription(), null) ? existingRooom.getDescription() : roomRequest.getDescription());
        existingRooom.setImageUrl(Objects.equals(roomRequest.getImageUrl(), null) ? existingRooom.getImageUrl() : roomRequest.getImageUrl());
        existingRooom.setCapacity(Objects.equals(roomRequest.getCapacity(), null) ? existingRooom.getCapacity() : roomRequest.getCapacity());
        existingRooom.setRoomType(Objects.equals(roomRequest.getRoomType(), null) ? existingRooom.getRoomType() : roomRequest.getRoomType());
        existingRooom.setPricePerNight(Objects.equals(roomRequest.getPricePerNight(), null) ? existingRooom.getPricePerNight() : roomRequest.getPricePerNight());
        return existingRooom;
    }
}
