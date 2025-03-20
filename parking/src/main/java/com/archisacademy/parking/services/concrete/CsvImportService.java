package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.model.Vehicle;
import com.archisacademy.parking.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CsvImportService {
    private final VehicleRepository vehicleRepository;
    public void parse(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                if (values.length < 4) { // There must be at least 4 values (registrationNumber, type, isActive, registrationDate).
                    System.err.println("Skipped row due to missing data: " + line);
                    continue;
                }
                Vehicle vehicle = new Vehicle();
                vehicle.setRegistrationNumber(values[0].trim());
                vehicle.setType(values[1].trim());
                String activeValue = values[2].trim().toLowerCase();
                vehicle.setActive(activeValue.equals("true") || activeValue.equals("1"));

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                    LocalDateTime registrationDate = LocalDate.parse(values[3].trim(), formatter).atStartOfDay();
                    vehicle.setRegistrationDate(Timestamp.valueOf(registrationDate));
                } catch (Exception e) {
                    System.err.println("Invalid date format, skipped row: " + line);
                    continue;
                }

                vehicleRepository.save(vehicle);
            }}}
}
