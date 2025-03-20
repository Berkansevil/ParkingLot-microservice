package com.archisacademy.parking_reservation.importer.excel;

import com.archisacademy.parking_reservation.entity.ParkingReservation;
import com.archisacademy.parking_reservation.repository.ParkingReservationRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.*;


@Component
public class ExcelParser {

    public void parse(MultipartFile file, ParkingReservationRepository reservationRepository) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) rowIterator.next();

            while (rowIterator.hasNext()) {
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    ParkingReservation reservation = new ParkingReservation();
                    reservation.setVehicleId((long) row.getCell(0).getNumericCellValue());
                    reservation.setParkingSpotId((long) row.getCell(1).getNumericCellValue());
                    reservation.setEndTime(row.getCell(2).getDateCellValue().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime());
                    reservationRepository.save(reservation);
                }
    }
        }
    }
}

