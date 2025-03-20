package com.archisacademy.parking_reservation.service.concretes;

import com.archisacademy.parking_reservation.entity.ParkingReservation;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CsvExporter {
    public byte[] exportToCsv(List<ParkingReservation> reservations) {
        try (StringWriter writer = new StringWriter();
             PrintWriter printWriter = new PrintWriter(writer)) {


            printWriter.printf("%-12s,%-15s,%-20s,%-20s%n",
                    "\"Vehicle ID\"", "\"Parking Spot ID\"", "\"Created At\"", "\"End Time\"");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (ParkingReservation reservation : reservations) {
                printWriter.printf("%-12s,%-15s,%-20s,%-20s%n",
                        "\"" + reservation.getVehicleId() + "\"",
                        "\"" + reservation.getParkingSpotId() + "\"",
                        "\"" + reservation.getCreatedAt().format(formatter) + "\"",
                        "\"" + reservation.getEndTime().format(formatter) + "\"");
            }

            return writer.toString().getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while generating CSV", e);
        }
    }
}
