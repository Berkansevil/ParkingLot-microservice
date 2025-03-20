package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.model.Vehicle;
import com.archisacademy.parking.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExporterService {
    private final VehicleRepository vehicleRepository;
    public byte[] exportToExcel() {
        List<Vehicle> vehicles=vehicleRepository.findAll();
        String[] headers = {"Registration Number", "Type", "Active", "Registration Date"};

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Vehicles");


            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);


            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));


            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }


            int rowIndex = 1;
            for (Vehicle vehicle : vehicles) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(vehicle.getRegistrationNumber());
                row.createCell(1).setCellValue(vehicle.getType());
                row.createCell(2).setCellValue(vehicle.isActive() ? "true" : "false");


                Cell dateCell = row.createCell(3);
                if (vehicle.getRegistrationDate() != null) {
                    dateCell.setCellValue(vehicle.getRegistrationDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    dateCell.setCellStyle(dateCellStyle);
                }
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            sheet.createFreezePane(0, 1);

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while generating Excel", e);
        }
    }

}
