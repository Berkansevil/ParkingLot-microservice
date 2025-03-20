package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.model.Vehicle;
import com.archisacademy.parking.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;


@Service
public class CsvExporterService {
    private final VehicleRepository vehicleRepository;
    public CsvExporterService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    public byte[] exportToCsv() {
        try (StringWriter writer = new StringWriter();
             PrintWriter printWriter = new PrintWriter(writer)) {

            // CSV Header
            printWriter.println("\"Registration Number\",\"Type\",\"Active\",\"Registration Date\"");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // CSV Rows
            for (Vehicle vehicle : vehicleRepository.findAll()) {
                printWriter.printf("\"%s\",\"%s\",\"%s\",\"%s\"%n",
                        vehicle.getRegistrationNumber(),
                        vehicle.getType(),
                        vehicle.isActive() ? "true" : "false",
                        vehicle.getRegistrationDate() != null ?
                                vehicle.getRegistrationDate().toLocalDateTime().format(formatter) : "");
            }

            return writer.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while generating CSV", e);
        }
    }

}
