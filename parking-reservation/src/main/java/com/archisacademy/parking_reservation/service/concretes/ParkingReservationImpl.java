package com.archisacademy.parking_reservation.service.concretes;

import com.archisacademy.parking_reservation.apiResponse.ApiResponse;
import com.archisacademy.parking_reservation.dto.request.ParkingReservationRequest;
import com.archisacademy.parking_reservation.dto.request.ParkingReservationUpdateRequest;
import com.archisacademy.parking_reservation.dto.response.ParkingReservationResponse;
import com.archisacademy.parking_reservation.dto.response.VehicleResponse;
import com.archisacademy.parking_reservation.entity.ParkingReservation;
import com.archisacademy.parking_reservation.entity.ParkingSpot;
import com.archisacademy.parking_reservation.exception.ParkingReservationNotFoundException;
import com.archisacademy.parking_reservation.client.ParkingSpotFeignClient;
import com.archisacademy.parking_reservation.client.VehicleFeignClient;
import com.archisacademy.parking_reservation.modelMapper.ModelMapperServiceImpl;
import com.archisacademy.parking_reservation.repository.ParkingReservationRepository;
import com.archisacademy.parking_reservation.service.abstracts.ParkingReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingReservationImpl implements ParkingReservationService {
    private final ParkingReservationRepository parkingReservationRepository;
    private final ModelMapperServiceImpl modelMapperService;
    private final VehicleFeignClient vehicleFeignClient;
    private final ParkingSpotFeignClient parkingSpotFeignClient;

    @Transactional()
    @Override
    public ApiResponse<ParkingReservationResponse> addParkingReservation( ParkingReservationRequest reservationRequest) {

        String vehiclePriority = getVehiclePriority(reservationRequest.getVehicleId());
        ParkingSpot parkingSpot = getParkingSpot(reservationRequest.getParkingSpotId());

        checkPriorityAndAccess(parkingSpot.getParkingSpotType(), vehiclePriority, parkingSpot.getAvailability());

        ResponseEntity<ApiResponse<VehicleResponse>> vehicleResponse= vehicleFeignClient.get(reservationRequest.getVehicleId());
        ResponseEntity<ParkingSpot> parkingSpotResponse=parkingSpotFeignClient.getParkingSpotById(reservationRequest.getParkingSpotId());
        ParkingReservation reservation=modelMapperService.request().map(reservationRequest, ParkingReservation.class);
        reservation.setParkingSpotId(parkingSpotResponse.getBody().getId());
        reservation.setVehicleId(reservationRequest.getVehicleId());
        ParkingReservation saved=parkingReservationRepository.save(reservation);

        parkingSpotFeignClient.updateAvailability(parkingSpot.getId(), false);

        ParkingReservationResponse response= modelMapperService.request().
                map(saved, ParkingReservationResponse.class);
        return new ApiResponse<>(true,"Parking reservation added successfully",response);
    }

    @Override
    public ApiResponse<ParkingReservationRequest> getParkingReservationById(long id) {
        Optional<ParkingReservation> reservation = parkingReservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new ParkingReservationNotFoundException("Parking Reservation not found for ID: " + id);
        }
        ParkingReservationRequest reservationRequest = modelMapperService.request().map(reservation.get(), ParkingReservationRequest.class);
        return new ApiResponse<>(true,"Parking reservation found successfully",reservationRequest);
    }

    @Override
    public ApiResponse<ParkingReservationResponse> updateParkingReservation(Long id,ParkingReservationUpdateRequest
                                                                                        reservationRequest) {
      ParkingReservation reservation=parkingReservationRepository.findById(id).get();
      modelMapperService.request().map(reservationRequest, reservation);
      ParkingReservation saved=parkingReservationRepository.save(reservation);
      ParkingReservationResponse response=modelMapperService.request().map(reservation, ParkingReservationResponse.class);
      return new ApiResponse<>(true,"Parking reservation updated successfully",response);
    }

    @Transactional
    @Override
    public ApiResponse<String> deleteParking(long id) {
        Optional<ParkingReservation> reservation = parkingReservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new ParkingReservationNotFoundException("Parking Reservation not found for ID: " + id);
        }
        parkingReservationRepository.delete(reservation.get());
        return new ApiResponse<>(true,"Parking reservation deleted successfully",null);
    }

    @Override
    public List<ParkingReservationResponse> getReservationsByVehicleId(Long vehicleId) {
        List<ParkingReservationResponse> responses=parkingReservationRepository.findByVehicleId(vehicleId)
                .stream()
                .map(reservation -> new ParkingReservationResponse(
                        reservation.getReservationId(),
                        reservation.getParkingSpotId(),
                        reservation.getCreatedAt(),
                        reservation.getEndTime()
                ))
                .collect(Collectors.toList());
        return responses;
    }

    @Scheduled(fixedRate = 86400000)
    private String getVehiclePriority(Long vehicleId) {
        ResponseEntity<String> vehiclePriorityResponse = vehicleFeignClient.getPriority(vehicleId);
        if (vehiclePriorityResponse.getBody() == null || !vehiclePriorityResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Vehicle priority not found");
        }
        return vehiclePriorityResponse.getBody();
    }
    @Scheduled(fixedRate = 86400000)
    private ParkingSpot getParkingSpot(Long parkingSpotId) {
        ResponseEntity<ParkingSpot> parkingSpotResponse = parkingSpotFeignClient.getParkingSpotById(parkingSpotId);
        if (parkingSpotResponse.getBody() == null || !parkingSpotResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Parking spot not found");
        }
        return parkingSpotResponse.getBody();
    }

    private void checkPriorityAndAccess(String parkingSpotType, String vehiclePriority, boolean isAvailable) {
        if ("HANDICAPPED".equals(parkingSpotType) && !"HANDICAPPED".equals(vehiclePriority)) {
            throw new RuntimeException("Only handicapped vehicles can reserve handicapped spots");
        }
        if ("PREMIUM".equals(parkingSpotType) && !"VIP".equals(vehiclePriority)) {
            throw new RuntimeException("Only VIP vehicles can reserve premium spots");
        }
        if (!isAvailable) {
            throw new RuntimeException("Parking spot is not available");
        }
    }

}
