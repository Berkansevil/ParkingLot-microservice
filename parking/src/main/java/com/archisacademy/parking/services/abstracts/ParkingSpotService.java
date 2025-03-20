package com.archisacademy.parking.services.abstracts;

import com.archisacademy.parking.dtos.request.ParkingSpotRequest;
import com.archisacademy.parking.dtos.request.ParkingSpotUpdateRequest;
import com.archisacademy.parking.dtos.response.ParkingSpotAvailabilityResponse;
import com.archisacademy.parking.dtos.response.ParkingSpotResponse;
import com.archisacademy.parking.dtos.response.ParkingSpotUpdateResponse;
import com.archisacademy.parking.model.ParkingSpot;

import java.util.List;

public interface ParkingSpotService {
    ParkingSpotResponse createParkingSpot(ParkingSpotRequest parkingSpotRequest);

    ParkingSpot getParkingSpotById(Long id);

    ParkingSpotUpdateResponse updateResponse(Long id, ParkingSpotUpdateRequest parkingSpotUpdateRequest);

    String deleteSpot(Long id);

    List<ParkingSpot> listSpots();

    ParkingSpotAvailabilityResponse isSpotAvailable(Long id);

    List<ParkingSpotResponse> searchParkingSpot(String parkingLocation);

    String getParkingSpotType(Long id);

    void updateAvailability(Long id, Boolean availability);

}
