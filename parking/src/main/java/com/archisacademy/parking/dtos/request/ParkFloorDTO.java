package com.archisacademy.parking.dtos.request;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ParkFloorDTO {
    private Long id;
    private int floor;
    private int capacity;
    private String floorBlock;
    private Long parkingId;

    public abstract ApiResponse save(ParkFloorDTO baseFloorDTO);

    public abstract ApiResponse findAll();

    public abstract ApiResponse findById(Long id);

    public abstract ApiResponse deleteById(Long id);
}