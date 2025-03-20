package com.archisacademy.parking.repositories;

import com.archisacademy.parking.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByParkingSpotLocationContainingIgnoreCase(String parkingSpotLocation);
}
