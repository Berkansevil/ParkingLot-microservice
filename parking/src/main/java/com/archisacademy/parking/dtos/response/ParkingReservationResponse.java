package com.archisacademy.parking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReservationResponse {
    private Long vehicleId;
    private Long parkingSpotId;
    private LocalDateTime createdAt;
    private LocalDateTime endTime;
}
