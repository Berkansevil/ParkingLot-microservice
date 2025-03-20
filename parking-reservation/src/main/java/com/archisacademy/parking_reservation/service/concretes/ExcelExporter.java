package com.archisacademy.parking_reservation.service.concretes;

import com.archisacademy.parking_reservation.entity.ParkingReservation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExporter {
    public byte[] exportToExcel(List<ParkingReservation> reservations) {
        String[] headers = {"Vehicle ID", "Parking Spot ID", "Created At", "End Time"};
        try (Workbook workbook = new XSSFWorkbook();

             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Parking Reservations");
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (ParkingReservation reservation : reservations) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(reservation.getVehicleId());
                row.createCell(1).setCellValue(reservation.getParkingSpotId());
                Cell createdAtCell = row.createCell(2);
                createdAtCell.setCellValue(reservation.getCreatedAt().format(formatter));
                createdAtCell.setCellStyle(dateCellStyle);
                Cell endTimeCell = row.createCell(3);
                endTimeCell.setCellValue(reservation.getEndTime().format(formatter));
                endTimeCell.setCellStyle(dateCellStyle);
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
