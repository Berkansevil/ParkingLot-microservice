package com.archisacademy.report_generation_service.services.Impl;

import com.archisacademy.report_generation_service.dtos.ParkingLotReportResponse;
import com.archisacademy.report_generation_service.dtos.ParkingLotUtilizationStatsResponse;
import com.archisacademy.report_generation_service.model.ParkingLotReport;
import com.archisacademy.report_generation_service.model.ParkingLotUsage;
import com.archisacademy.report_generation_service.repositories.ParkingLotReportRepository;
import com.archisacademy.report_generation_service.services.ParkingLotReportService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ParkingLotReportServiceImpl implements ParkingLotReportService {
    private static Font COURIER = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
    private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
    private static Font COURIER_SMALL_FOOTER = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
    @Value("${pdfDir}")
    private String pdfDir;

    @Value("${csvDir}")
    private String csvDir;

    @Value("${reportFileName}")
    private String reportFileName;

    @Value("${reportFileNameDateFormat}")
    private String reportFileNameDateFormat;

    @Value("${localDateFormat}")
    private String localDateFormat;

    @Value("${logoImgPath}")
    private String logoImgPath;

    @Value("${logoImgScale}")
    private Float[] logoImgScale;

    @Value("${currencySymbol:}")
    private String currencySymbol;

    @Value("${table_noOfColumns}")
    private int noOfColumns;

    @Value("${table.columnNames}")
    private List<String> columnNames;



    private final ParkingLotReportRepository parkingLotRepository;

    public ParkingLotReportServiceImpl(ParkingLotReportRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    @Override
    public ParkingLotReportResponse generateParkingLotReport() {
        ParkingLotReportResponse response = new ParkingLotReportResponse();
        List<ParkingLotReport> parkingLots = parkingLotRepository.findAll();
        if (parkingLots.isEmpty()) {
            return response;
        }
        ParkingLotReport parkingLot = parkingLots.getFirst();
        response.setParkingLotName(parkingLot.getParkingLotName());
        response.setLocation(parkingLot.getLocation());
        response.setTotalCapacity(parkingLot.getTotalCapacity());
        response.setCurrentUsage(parkingLot.getCurrentUsage());
        response.setRevenue(parkingLot.getRevenue());
        return response;
    }

    @Override
    public void generateCSVReport() {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(getCsvFileNameWithDate())) {

            Sheet sheet = createSheet(workbook);
            addHeaderRow(sheet);
            addDataRows(sheet);

            workbook.write(fileOut);
            System.out.println("------------------Your CSV Report is ready!-------------------------");

        } catch (IOException e) {
            throw new RuntimeException("Error writing Excel report", e);
        }
    }

    private Sheet createSheet(SXSSFWorkbook workbook) {
        return workbook.createSheet("Parking Lot Report");
    }

    private void addHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Parking Lot Name", "Location", "Total Capacity", "Current Usage", "Revenue", "Usage History"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private void addDataRows(Sheet sheet) {
        List<ParkingLotReport> parkingLots = parkingLotRepository.findAll();
        for (int i = 0; i < parkingLots.size(); i++) {
            ParkingLotReport parkingLot = parkingLots.get(i);
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(parkingLot.getParkingLotName());
            dataRow.createCell(1).setCellValue(parkingLot.getLocation());
            dataRow.createCell(2).setCellValue(parkingLot.getTotalCapacity());
            dataRow.createCell(3).setCellValue(parkingLot.getCurrentUsage());
            dataRow.createCell(4).setCellValue(currencySymbol + parkingLot.getRevenue());
            dataRow.createCell(5).setCellValue(parkingLot.getUsageHistory().toString());
        }
    }

    private String getCsvFileNameWithDate() {
        String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
        return csvDir + reportFileName + "-" + localDateString + ".xlsx";
    }

    @Override
    public void generatePDFReport() {

            Document document = new Document();

            try {
                PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate()));
                document.open();
                addLogo(document);
                addDocTitle(document);
                createTable(document,noOfColumns);
                addFooter(document);
                document.close();
                System.out.println("------------------Your PDF Report is ready!-------------------------");

            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

    }

        private void addLogo(Document document) {
            try {
                Image img = Image.getInstance(logoImgPath);
                img.scalePercent(logoImgScale[0], logoImgScale[1]);
                img.setAlignment(Element.ALIGN_RIGHT);
                document.add(img);
            } catch (DocumentException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private void addDocTitle(Document document) throws DocumentException {
            String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateFormat));
            Paragraph p1 = new Paragraph();
            leaveEmptyLine(p1, 1);
            p1.add(new Paragraph(reportFileName, COURIER));
            p1.setAlignment(Element.ALIGN_CENTER);
            leaveEmptyLine(p1, 1);
            p1.add(new Paragraph("Report generated on " + localDateString, COURIER_SMALL));

            document.add(p1);

        }

        private void createTable(Document document, int noOfColumns) throws DocumentException {
            Paragraph paragraph = new Paragraph();
            leaveEmptyLine(paragraph, 3);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(noOfColumns);

            for(int i=0; i<noOfColumns; i++) {
                PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.CYAN);
                table.addCell(cell);
            }

            table.setHeaderRows(1);
            getDbData(table);
            document.add(table);
        }

        private void getDbData(PdfPTable table) {

            List<ParkingLotReport> list = parkingLotRepository.findAll();
            for (ParkingLotReport parkingLotReport : list) {

                table.setWidthPercentage(100);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

                table.addCell(parkingLotReport.getParkingLotName());
                table.addCell(parkingLotReport.getLocation());
                table.addCell(String.valueOf(parkingLotReport.getTotalCapacity()));
                table.addCell(String.valueOf(parkingLotReport.getCurrentUsage()));
                table.addCell(currencySymbol + parkingLotReport.getRevenue());
                table.addCell(parkingLotReport.getUsageHistory().toString());
            }

        }

        private void addFooter(Document document) throws DocumentException {
            Paragraph p2 = new Paragraph();
            leaveEmptyLine(p2, 3);
            p2.setAlignment(Element.ALIGN_MIDDLE);
            p2.add(new Paragraph(
                    "------------------------End Of " +reportFileName+"------------------------",
                    COURIER_SMALL_FOOTER));

            document.add(p2);
        }

        private static void leaveEmptyLine(Paragraph paragraph, int number) {
            for (int i = 0; i < number; i++) {
                paragraph.add(new Paragraph(" "));
            }
        }

        private String getPdfNameWithDate() {
            String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
            return pdfDir+reportFileName+"-"+localDateString+".pdf";
        }


    @Scheduled(cron = "0 33 22 * * ?")
    public void generateDailyReports() {
        System.out.println("Daily report generation started");
        try {
            generateCSVReport();
            generatePDFReport();
            System.out.println("Reports generated successfully");
        } catch (Exception e) {
            System.err.println("Error generating reports: " + e.getMessage());
            throw new RuntimeException("Error generating reports", e);
        }
    }

    @Override
    public ParkingLotUtilizationStatsResponse getUtilizationStats(Long parkingLotId) {
        Optional<ParkingLotReport> reportOptional = parkingLotRepository.findById(parkingLotId);
        if (reportOptional.isPresent()) {
            ParkingLotReport report = reportOptional.get();
            Double occupancyRate = (double) report.getCurrentUsage() / report.getTotalCapacity() * 100;
            String peakTime = calculatePeakTime(report.getUsageHistory());

            return new ParkingLotUtilizationStatsResponse(
                    report.getParkingLotName(),
                    report.getLocation(),
                    report.getTotalCapacity(),
                    report.getCurrentUsage(),
                    report.getRevenue(),
                    Collections.singletonList(peakTime),
                    occupancyRate,
                    peakTime
            );
        }
        throw new RuntimeException("Parking lot report not found");
    }

    private String calculatePeakTime(List<ParkingLotUsage> usageHistory) {
        if (usageHistory == null || usageHistory.isEmpty()) {
            return "No data available";
        }
        Map<String, Long> hourlyUsage = new HashMap<>();

        for (ParkingLotUsage usage : usageHistory) {
            String hour = usage.getTimestamp().toString().substring(0, 13);
            hourlyUsage.put(hour, hourlyUsage.getOrDefault(hour, 0L) + usage.getNumberOfVehicles());
        }

        String peakHour = null;
        Long maxVehicles = 0L;

        for (Map.Entry<String, Long> entry : hourlyUsage.entrySet()) {
            if (entry.getValue() > maxVehicles) {
                maxVehicles = entry.getValue();
                peakHour = entry.getKey();
            }
        }

        return peakHour != null ? peakHour + ":00" : "No peak time found";
    }
}