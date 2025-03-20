package com.archisacademy.parking.client;

import com.archisacademy.parking.dtos.response.ParkingReservationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "reservation-service",url  = "http://localhost:8090/api/v1/parking/reservation")
public interface ParkingReservationFeignClient {
    @GetMapping("vehicle/{vehicleId}")
    List<ParkingReservationResponse> getReservationsByVehicleId(@PathVariable long vehicleId);

}
