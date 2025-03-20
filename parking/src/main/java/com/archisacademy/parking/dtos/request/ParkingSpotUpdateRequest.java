package com.archisacademy.parking.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotUpdateRequest {
    @NotBlank(message = "parking spot type cannot be null!!")
    private String parkingSpotType;

    @NotNull(message = "parking spot availabilty cannot be empty!!!")
    private Boolean parkingSpotAvailability;

    @NotBlank(message = "parking spot location cannot be empty!!!")
    private String parkingSpotLocation;
}
