package com.archisacademy.parking.repositories;

import com.archisacademy.parking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByParkingLotId(Long parkingLotId);
}
