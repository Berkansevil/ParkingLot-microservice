package com.archisacademy.parking_reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReservationResponse {
    private Long vehicleId;
    private Long parkingSpotId;
    private LocalDateTime createdAt;
    private LocalDateTime endTime;
}
