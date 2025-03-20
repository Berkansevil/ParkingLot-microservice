package com.archisacademy.parking.controllers;

import com.archisacademy.parking.dtos.request.ParkingSpotRequest;
import com.archisacademy.parking.dtos.request.ParkingSpotUpdateRequest;
import com.archisacademy.parking.dtos.response.ParkingSpotAvailabilityResponse;
import com.archisacademy.parking.dtos.response.ParkingSpotResponse;
import com.archisacademy.parking.dtos.response.ParkingSpotUpdateResponse;
import com.archisacademy.parking.model.ParkingSpot;
import com.archisacademy.parking.services.abstracts.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingspots")
public class ParkingSpotController {
    private final ParkingSpotService parkingSpotService;


    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping("/save")
    public ResponseEntity<ParkingSpotResponse> createParkingSpot(@RequestBody ParkingSpotRequest parkingSpotRequest){
        ParkingSpotResponse response = parkingSpotService.createParkingSpot(parkingSpotRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable Long id){
        ParkingSpot parkingSpot = parkingSpotService.getParkingSpotById(id);
        return ResponseEntity.ok(parkingSpot);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ParkingSpotUpdateResponse> updateSpot(@PathVariable Long id, @RequestBody ParkingSpotUpdateRequest parkingSpotUpdateRequest){
        ParkingSpotUpdateResponse response = parkingSpotService.updateResponse(id, parkingSpotUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteParkingSpot(@PathVariable Long id){
        try {
            String response = parkingSpotService.deleteSpot(id);
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Parking spot not found with id:" +id);
        }
    }
    //list parking spots
    @GetMapping("/parking-spots")
    public ResponseEntity<List<ParkingSpot>> listSpots(){
        List<ParkingSpot> parkingSpots = parkingSpotService.listSpots();
        return ResponseEntity.ok(parkingSpots);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<ParkingSpotAvailabilityResponse> isSpotAvailable(@PathVariable Long id){
        ParkingSpotAvailabilityResponse response = parkingSpotService.isSpotAvailable(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("search")
    public ResponseEntity<List<ParkingSpotResponse>> searchParkingSpot(@RequestParam String parkingLocation){
            List<ParkingSpotResponse> responses=parkingSpotService.searchParkingSpot(parkingLocation);
            return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-type/{id}")
    public ResponseEntity<String> getParkingSpotType(@PathVariable Long id){
        String parkingSpotType = parkingSpotService.getParkingSpotType(id);
        return ResponseEntity.ok(parkingSpotType);
    }

    @PutMapping("/update-availability/{id}")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestBody Boolean availability) {
        parkingSpotService.updateAvailability(id, availability);
        return ResponseEntity.ok().build();
    }

}
