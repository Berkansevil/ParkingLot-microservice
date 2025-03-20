package com.archisacademy.parking.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleUpdateRequest {

    private String registrationNumber;

    private String type;

    private boolean isActive;

}
