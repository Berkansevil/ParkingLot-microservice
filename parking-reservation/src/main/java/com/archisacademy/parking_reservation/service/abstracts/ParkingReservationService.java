package com.archisacademy.parking_reservation.service.abstracts;

import com.archisacademy.parking_reservation.apiResponse.ApiResponse;
import com.archisacademy.parking_reservation.dto.request.ParkingReservationRequest;
import com.archisacademy.parking_reservation.dto.request.ParkingReservationUpdateRequest;
import com.archisacademy.parking_reservation.dto.response.ParkingReservationResponse;
import com.archisacademy.parking_reservation.entity.ParkingReservation;

import java.util.List;

public interface ParkingReservationService {
    ApiResponse<ParkingReservationResponse> addParkingReservation(ParkingReservationRequest reservationRequest);
    ApiResponse<ParkingReservationRequest> getParkingReservationById(long id);
    ApiResponse<ParkingReservationResponse> updateParkingReservation(Long id,ParkingReservationUpdateRequest reservationRequest);
    ApiResponse<String>  deleteParking(long id);
    List<ParkingReservationResponse> getReservationsByVehicleId(Long vehicleId);
}
