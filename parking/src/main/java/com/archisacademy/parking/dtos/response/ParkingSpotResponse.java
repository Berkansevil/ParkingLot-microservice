package com.archisacademy.parking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotResponse {
    private Long id;
    private String parkingSpotType;
    private Boolean parkingSpotAvailability;
    private String parkingSpotLocation;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
