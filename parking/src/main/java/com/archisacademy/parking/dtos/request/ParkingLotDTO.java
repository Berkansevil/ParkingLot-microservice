package com.archisacademy.parking.dtos.request;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public abstract class ParkingLotDTO {

    private String parkingLotName;

    private String location;


    private Long totalCapacity;


    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Long totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public abstract ApiResponse save(ParkingLotDTO parkingLotDTO);

    public abstract ApiResponse findAll();

    public abstract ApiResponse findById(Long id);

    public abstract ApiResponse deleteById(Long id);
}