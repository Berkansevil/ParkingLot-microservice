package com.archisacademy.parking.repositories;

import com.archisacademy.parking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking>findByUserId(Long id);
}
