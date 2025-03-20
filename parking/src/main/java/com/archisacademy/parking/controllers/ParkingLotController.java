package com.archisacademy.parking.controllers;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.ParkingLotDTO;
import com.archisacademy.parking.services.concrete.ParkingLotServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/parking")
public class ParkingLotController {

    private final ParkingLotServiceImpl parkingLotService;

    public ParkingLotController(ParkingLotServiceImpl parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody ParkingLotDTO dto) {
        ApiResponse save = parkingLotService.save(dto);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }


    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all() {
        ApiResponse save = parkingLotService.findAll();
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ApiResponse save = parkingLotService.findById(id);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse save = parkingLotService.deleteById(id);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

}