package com.archisacademy.parking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {

    private String registrationNumber;

    private String type;

    private boolean isActive;

    private Timestamp registrationDate;

}
