package com.archisacademy.parking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotUpdateResponse {
    private Long id;
    private String parkingSpotType;
    private Boolean parkingSpotAvailability;
    private String parkingSpotLocation;
    private LocalDate updatedAt;
}
