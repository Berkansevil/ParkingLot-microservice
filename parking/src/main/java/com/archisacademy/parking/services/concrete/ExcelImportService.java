package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.model.Vehicle;
import com.archisacademy.parking.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class ExcelImportService {
    private final VehicleRepository vehicleRepository;

    public void parse(MultipartFile file) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Vehicle vehicle = new Vehicle();
                Cell regNumberCell = row.getCell(0);
                if (regNumberCell == null || regNumberCell.getCellType() != CellType.STRING) continue;
                vehicle.setRegistrationNumber(regNumberCell.getStringCellValue().trim());

                Cell typeCell = row.getCell(1);
                if (typeCell == null || typeCell.getCellType() != CellType.STRING) continue;
                vehicle.setType(typeCell.getStringCellValue().trim());
                Cell activeCell = row.getCell(2);
                boolean isActive = false;
                if (activeCell != null) {
                    if (activeCell.getCellType() == CellType.BOOLEAN) {
                        isActive = activeCell.getBooleanCellValue();
                    } else if (activeCell.getCellType() == CellType.STRING) {
                        isActive = Boolean.parseBoolean(activeCell.getStringCellValue().trim());
                    }
                }
                vehicle.setActive(isActive);
                Cell dateCell = row.getCell(3);
                Timestamp registrationDate = null;
                if (dateCell != null) {
                    try {
                        if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                            registrationDate = new Timestamp(dateCell.getDateCellValue().getTime());
                        } else if (dateCell.getCellType() == CellType.STRING) {
                            registrationDate = Timestamp.valueOf(dateCell.getStringCellValue().trim());
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                vehicle.setRegistrationDate(registrationDate);
                vehicleRepository.save(vehicle);
            }

        }
    }
}
