package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.ParkingLotDTO;
import com.archisacademy.parking.model.ParkingLot;
import com.archisacademy.parking.repositories.ParkingLotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ParkingLotServiceImpl extends ParkingLotDTO  {
    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotServiceImpl(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;

    }

    @Override
    public ApiResponse save(ParkingLotDTO parkingLotDTO) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setTotalCapasity(parkingLotDTO.getTotalCapacity());
        parkingLot.setParkingLotName(parkingLotDTO.getParkingLotName());
        parkingLot.setLocation(parkingLotDTO.getLocation());
        parkingLotRepository.save(parkingLot);
        return new ApiResponse<>(true,"Parking lot saved successfully", parkingLot);
    }

    @Override
    public ApiResponse findAll() {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        return new ApiResponse<>(true,"Parking lots found", parkingLots);
    }

    @Override
    public ApiResponse findById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findById(id).orElse(null);
        if (parkingLot == null) {
            return new ApiResponse<>(false,"Parking lot not found", parkingLot);
        }
        return new ApiResponse<>(true,"Parking lot found", parkingLot);
    }

    @Override
    public ApiResponse deleteById(Long id) {
        if (parkingLotRepository.existsById(id)) {
            parkingLotRepository.deleteById(id);
        }
        return null;
    }
}