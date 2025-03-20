package com.archisacademy.parking_reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingReservationUpdateRequest {
    private LocalDateTime createdAt;
    private LocalDateTime endTime;
}
