package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.dtos.request.ParkingSpotRequest;
import com.archisacademy.parking.dtos.request.ParkingSpotUpdateRequest;
import com.archisacademy.parking.dtos.response.ParkingSpotAvailabilityResponse;
import com.archisacademy.parking.dtos.response.ParkingSpotResponse;
import com.archisacademy.parking.dtos.response.ParkingSpotUpdateResponse;
import com.archisacademy.parking.exception.ParkingSpotNotFoundException;
import com.archisacademy.parking.model.ParkingSpot;
import com.archisacademy.parking.modelmapper.ModelMapperService;
import com.archisacademy.parking.repositories.ParkingSpotRepository;
import com.archisacademy.parking.services.abstracts.ParkingSpotService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ModelMapperService modelMapperService;

    public ParkingSpotServiceImpl(ParkingSpotRepository parkingSpotRepository, ModelMapperService modelMapperService) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public ParkingSpotResponse createParkingSpot(ParkingSpotRequest parkingSpotRequest){
        ParkingSpot parkingSpot = modelMapperService.request().map(parkingSpotRequest, ParkingSpot.class);
        ParkingSpot savedParkingSpot = parkingSpotRepository.save(parkingSpot);
        ParkingSpotResponse parkingSpotResponse = modelMapperService.response().map(savedParkingSpot, ParkingSpotResponse.class);
        return parkingSpotResponse;
    }

    @Override
    public ParkingSpot getParkingSpotById(Long id){
        ParkingSpot parkingSpot = parkingSpotRepository.findById(id).orElseThrow(()-> new RuntimeException("parking spot cannot be found!!!" + id));
        return parkingSpot;
    }

    @Override
    public ParkingSpotUpdateResponse updateResponse(Long id, ParkingSpotUpdateRequest parkingSpotUpdateRequest){
        ParkingSpot parkingSpot = parkingSpotRepository.findById(id).orElseThrow(()-> new RuntimeException("parking spot cannot be found!!!"));
        modelMapperService.request().map(parkingSpotUpdateRequest, ParkingSpot.class);
        ParkingSpot updatedSpot = parkingSpotRepository.save(parkingSpot);
        ParkingSpotUpdateResponse response = modelMapperService.response().map(updatedSpot, ParkingSpotUpdateResponse.class);
        return response;
    }

    @Override
    public String deleteSpot(Long id){

        ParkingSpot parkingSpot = parkingSpotRepository.findById(id).orElseThrow(()-> new RuntimeException("Parking spot cannot be found!!!" + id));
        parkingSpotRepository.deleteById(id);
        return "Parking Spot has been deleten Successfully";
    }

    @Override
    public List<ParkingSpot> listSpots(){
       return parkingSpotRepository.findAll();
    }

   @Override
    public ParkingSpotAvailabilityResponse isSpotAvailable(Long id){
        ParkingSpot parkingSpot = parkingSpotRepository.findById(id).orElseThrow(()-> new RuntimeException("Parking spot cannot be found!!!"));
        return modelMapperService.response().map(parkingSpot, ParkingSpotAvailabilityResponse.class);
   }

    @Override
    public List<ParkingSpotResponse> searchParkingSpot(String parkingLocation) {
        List<ParkingSpotResponse> spotResponses=parkingSpotRepository.findByParkingSpotLocationContainingIgnoreCase(parkingLocation)
                .stream()
                .map(parkingSpot -> new ParkingSpotResponse(
                        parkingSpot.getId(),
                        parkingSpot.getParkingSpotType(),
                        parkingSpot.getAvailability(),
                        parkingSpot.getParkingSpotLocation(),
                        parkingSpot.getCreatedAt(),
                        parkingSpot.getUpdatedAt()
                ))
                .collect(Collectors.toList());
        return spotResponses;
    }

    @Override
    public String getParkingSpotType(Long id) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot cannot be found!"));

        if (parkingSpot.getParkingSpotType().equals("HANDICAPPED") || parkingSpot.getParkingSpotType().equals("PREMIUM")) {
            return parkingSpot.getParkingSpotType();
        }

        return "REGULAR";
    }

    @Override
    public void updateAvailability(Long id, Boolean availability) {
        ParkingSpot parkingSpot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot cannot be found!"));

        parkingSpot.setAvailability(availability);
        parkingSpotRepository.save(parkingSpot);
    }

}
