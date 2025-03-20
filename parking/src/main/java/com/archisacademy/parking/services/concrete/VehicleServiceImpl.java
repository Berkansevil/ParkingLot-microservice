package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.client.ParkingReservationFeignClient;
import com.archisacademy.parking.dtos.response.ParkingReservationResponse;
import com.archisacademy.parking.modelmapper.ModelMapperService;
import com.archisacademy.parking.dtos.request.VehicleRequest;
import com.archisacademy.parking.dtos.request.VehicleUpdateRequest;
import com.archisacademy.parking.dtos.response.VehicleResponse;
import com.archisacademy.parking.exception.VehicleNotFoundException;
import com.archisacademy.parking.model.Vehicle;
import com.archisacademy.parking.repositories.VehicleRepository;
import com.archisacademy.parking.services.abstracts.VehicleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapperService modelMapperService;
    private final ParkingReservationFeignClient reservationFeignClient;
    private static final Logger logger = LogManager.getLogger(VehicleServiceImpl.class);


    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapperService modelMapperService,
                              ParkingReservationFeignClient reservationFeignClient) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapperService = modelMapperService;
        this.reservationFeignClient = reservationFeignClient;
    }

    @Override
    public ApiResponse<VehicleResponse> save(VehicleRequest vehicleRequest) {

        Vehicle vehicle = modelMapperService.request().map(vehicleRequest, Vehicle.class);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        logger.info("Vehicle saved: {}", savedVehicle);
        VehicleResponse vehicleResponse = modelMapperService.response().map(savedVehicle, VehicleResponse.class);

        return new ApiResponse<>(true, "Vehicle saved successfully.", vehicleResponse);
    }


    @Override
    public ApiResponse<VehicleResponse> update(Long vehicleId, VehicleUpdateRequest vehicleUpdateRequest) {

        Vehicle vehicle = getVehicleById(vehicleId);
        logger.debug("Found vehicle to update: {}", vehicle);
        modelMapperService.request().map(vehicleUpdateRequest, vehicle);

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        logger.info("Vehicle with ID {} updated: {}", vehicleId, updatedVehicle);
        VehicleResponse vehicleResponse = modelMapperService.response().map(updatedVehicle, VehicleResponse.class);

        return new ApiResponse<>(true,"Vehicle updated successfully.", vehicleResponse);
    }

    @Override
    public ApiResponse<String> delete(Long vehicleId) {

        Vehicle vehicle = getVehicleById(vehicleId);
        logger.debug("Found vehicle to delete: {}", vehicle);
        vehicleRepository.delete(vehicle);
        logger.info("Vehicle with ID {} deleted successfully.", vehicleId);
        return new ApiResponse<>(true,"Vehicle deleted successfully.");
    }

    @Override
    public ApiResponse<VehicleResponse> get(Long vehicleId) {

        Vehicle vehicle = getVehicleById(vehicleId);
        logger.debug("Found vehicle: {}", vehicle);
        VehicleResponse vehicleResponse = modelMapperService.response().map(vehicle, VehicleResponse.class);

        return new ApiResponse<>(true,"Vehicle found.", vehicleResponse);
    }

    @Override
    @Scheduled(cron = "0 0 0 1/2 * ?")
    public ApiResponse<List<VehicleResponse>> getAll() {
        logger.info("Fetching all vehicles");
        List<Vehicle> vehicles = vehicleRepository.findAll();

        List<VehicleResponse> vehicleResponseList = vehicles.stream()
                .map(vehicle -> modelMapperService.response().map(vehicle, VehicleResponse.class))
                .collect(Collectors.toList());

        return new ApiResponse<>(true,"Vehicles found.", vehicleResponseList);
    }



    private Vehicle getVehicleById(Long vehicleId) {
        logger.info("Fetching vehicle by ID {}", vehicleId);

        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> {
                    logger.error("Vehicle not found with ID {}", vehicleId);
                    return new VehicleNotFoundException("Vehicle not found with id: " + vehicleId);
                });
    }

    @Override
    public List<ParkingReservationResponse> getParkingReservations(Long vehicleId) {{
            return reservationFeignClient.getReservationsByVehicleId(vehicleId);
        }
    }

    @Override
    public String getVehiclePriority(Long vehicleId) {

        Vehicle vehicle = getVehicleById(vehicleId);

        if (vehicle.getPriority().equals("VIP") || vehicle.getPriority().equals("HANDICAPPED")) {
            return vehicle.getPriority();
        }

        return "No priority!";
    }

}
