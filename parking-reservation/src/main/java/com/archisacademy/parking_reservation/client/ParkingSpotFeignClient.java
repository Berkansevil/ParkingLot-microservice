package com.archisacademy.parking_reservation.client;

import com.archisacademy.parking_reservation.entity.ParkingSpot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "parkingSpot-service",url = "http://localhost:8081")
public interface ParkingSpotFeignClient {
    @GetMapping("/api/v1/parkingspots/{id}")
    ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable Long id);

    @GetMapping("/api/v1/parkingspots/get-type/{id}")
    ResponseEntity<ParkingSpot> getParkingSpotType(@PathVariable Long id);

    @PutMapping("/api/v1/parkingspots/update-availability/{id}")
    ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestBody Boolean availability);

}
