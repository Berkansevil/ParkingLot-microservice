package com.archisacademy.parking.repositories;

import com.archisacademy.parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
