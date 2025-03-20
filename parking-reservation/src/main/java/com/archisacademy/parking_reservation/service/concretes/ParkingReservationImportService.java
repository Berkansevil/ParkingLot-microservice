package com.archisacademy.parking_reservation.service.concretes;

import com.archisacademy.parking_reservation.importer.csv.CsvParser;
import com.archisacademy.parking_reservation.importer.excel.ExcelParser;
import com.archisacademy.parking_reservation.repository.ParkingReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ParkingReservationImportService {

    private final ParkingReservationRepository reservationRepository;
    private final CsvParser csvParser;
    private final ExcelParser excelParser;

    public ParkingReservationImportService(ParkingReservationRepository reservationRepository,
                                           CsvParser csvParser, ExcelParser excelParser) {
        this.reservationRepository = reservationRepository;
        this.csvParser = csvParser;
        this.excelParser = excelParser;
    }

    public void importFile(MultipartFile file) throws IOException {
        String fileType = getFileExtension(file);

        if ("csv".equalsIgnoreCase(fileType)) {
            csvParser.parse(file, reservationRepository);
        } else if ("xlsx".equalsIgnoreCase(fileType)) {
            excelParser.parse(file, reservationRepository);
        } else {
            throw new UnsupportedOperationException("Unsupported file format");
        }
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
