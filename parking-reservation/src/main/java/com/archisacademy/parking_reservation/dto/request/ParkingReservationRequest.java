package com.archisacademy.parking_reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingReservationRequest {
    @NotNull(message = "Vehicle ID cannot be null")
    private Long vehicleId;

    @NotNull(message = "Parking Spot ID cannot be null")
    private Long parkingSpotId;

    @NotNull(message = "Start Time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @NotNull(message = "End Time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    @AssertTrue(message = "End Time must be after Start Time")
    public boolean isEndTimeValid() {
        return createdAt == null || endTime == null || endTime.isAfter(createdAt);
    }
}
