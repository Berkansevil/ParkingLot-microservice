package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.ParkFloorDTO;
import com.archisacademy.parking.model.ParkFloor;
import com.archisacademy.parking.model.ParkingLot;
import com.archisacademy.parking.repositories.ParkFloorRepository;
import com.archisacademy.parking.repositories.ParkingLotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkFloorService extends ParkFloorDTO {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkFloorRepository parkingFloorRepository;

    public ParkFloorService(ParkingLotRepository parkingLotRepository, ParkFloorRepository parkingFloorRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingFloorRepository = parkingFloorRepository;
    }


    @Override
    public ApiResponse save(ParkFloorDTO baseFloorDTO) {
        ParkFloor baseFloorParking = new ParkFloor();
        baseFloorParking.setFloor(baseFloorDTO.getFloor());
        baseFloorParking.setCapacity(baseFloorDTO.getCapacity());
        baseFloorParking.setFloorBlock(baseFloorDTO.getFloorBlock());
        ParkingLot parkingLot  = null;
        if (baseFloorDTO.getParkingId() != null) {
            parkingLot = parkingLotRepository.findById(baseFloorDTO.getParkingId()).orElse(null);
            if (parkingLot == null) {
                return new ApiResponse(false, "Parking not found");
            }
        }
        baseFloorParking.setParkingLot(parkingLot);
        parkingFloorRepository.save(baseFloorParking);
        return null;
    }

    @Override
    public ApiResponse findAll() {
        List<ParkFloor> baseFloorParkings = parkingFloorRepository.findAll();
        if (baseFloorParkings.isEmpty()) {
            return new ApiResponse(false, "No data found");
        }
        return new ApiResponse<>(true,"Parking found", baseFloorParkings);
    }

    @Override
    public ApiResponse findById(Long id) {
        ParkFloor baseFloorParking = parkingFloorRepository.findById(id).orElse(null);
        if (baseFloorParking == null) {
            return new ApiResponse(false, "Parking not found");
        }
        return new ApiResponse<>(true,"Parking found", baseFloorParking);
    }

    @Override
    public ApiResponse deleteById(Long id) {
        if (parkingFloorRepository.existsById(id)) {
            parkingFloorRepository.deleteById(id);
        }
        return null;
    }
}
