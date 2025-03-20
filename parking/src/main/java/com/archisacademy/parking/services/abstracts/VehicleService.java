package com.archisacademy.parking.services.abstracts;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.VehicleRequest;
import com.archisacademy.parking.dtos.request.VehicleUpdateRequest;
import com.archisacademy.parking.dtos.response.ParkingReservationResponse;
import com.archisacademy.parking.dtos.response.VehicleResponse;

import java.util.List;

public interface VehicleService {

    ApiResponse<VehicleResponse> save(VehicleRequest vehicleRequest);

    ApiResponse<VehicleResponse> update(Long vehicleId, VehicleUpdateRequest vehicleUpdateRequest);

    ApiResponse<String> delete(Long vehicleId);

    ApiResponse<VehicleResponse> get(Long vehicleId);

    ApiResponse<List<VehicleResponse>> getAll();

    List<ParkingReservationResponse> getParkingReservations(Long vehicleId);

    String getVehiclePriority(Long vehicleId);

}
