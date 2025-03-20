package com.archisacademy.parking.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {

    @NotNull(message = "Registration number is required.")
    private String registrationNumber;

    @NotNull(message = "Vehicle type is required.")
    private String type;

    private boolean isActive;

    @NotNull(message = "Vehicle priority is required.")
    private String priority;

}
