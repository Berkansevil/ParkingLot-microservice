package com.archisacademy.parking_reservation.importer.csv;

import com.archisacademy.parking_reservation.entity.ParkingReservation;
import com.archisacademy.parking_reservation.repository.ParkingReservationRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

@Component
public class CsvParser {

    public void parse(MultipartFile file, ParkingReservationRepository reservationRepository) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                ParkingReservation reservation = new ParkingReservation();
                reservation.setVehicleId(Long.parseLong(values[0]));
                reservation.setParkingSpotId(Long.parseLong(values[1]));
                reservation.setEndTime(LocalDateTime.parse(values[2]));
                reservationRepository.save(reservation);
            }
        }
    }
}
