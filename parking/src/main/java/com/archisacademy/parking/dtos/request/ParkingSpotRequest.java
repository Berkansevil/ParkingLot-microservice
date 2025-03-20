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
public class ParkingSpotRequest {
    @NotBlank(message = "Parking Spot Type cannot be blank!!!")
    private String parkingSpotType;

    @NotNull(message = "Parking spot availability cannot be null!!!")
    private Boolean parkingSpotAvailability;

    @NotBlank(message = "Parking spot location cannot be null!!!")
    private String parkingSpotLocation;
}
