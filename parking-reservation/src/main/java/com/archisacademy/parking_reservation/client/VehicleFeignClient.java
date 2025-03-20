package com.archisacademy.parking_reservation.client;

import com.archisacademy.parking_reservation.apiResponse.ApiResponse;
import com.archisacademy.parking_reservation.dto.response.VehicleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle-service",url = "http://localhost:8081")
public interface VehicleFeignClient {
    @GetMapping("/api/vehicles/{vehicleId}")
    ResponseEntity<ApiResponse<VehicleResponse>> get(@PathVariable Long vehicleId);

    @GetMapping("/api/vehicles/priority/{vehicleId}")
    ResponseEntity<String> getPriority(@PathVariable Long vehicleId);
}
