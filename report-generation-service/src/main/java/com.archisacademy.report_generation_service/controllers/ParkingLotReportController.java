package com.archisacademy.report_generation_service.controllers;

import com.archisacademy.report_generation_service.apiResponse.ApiResponse;
import com.archisacademy.report_generation_service.dtos.ParkingLotReportResponse;
import com.archisacademy.report_generation_service.dtos.ParkingLotUtilizationStatsResponse;
import com.archisacademy.report_generation_service.model.ParkingLotReport;
import com.archisacademy.report_generation_service.services.ParkingLotReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
public class ParkingLotReportController {

    private final ParkingLotReportService parkingLotReportService;

    public ParkingLotReportController(ParkingLotReportService parkingLotReportService) {
        this.parkingLotReportService = parkingLotReportService;
    }

    @GetMapping("/parking-lot")
    public ResponseEntity<ApiResponse<ParkingLotReportResponse>> generateParkingLotReport() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Parking lot report generated successfully", parkingLotReportService.generateParkingLotReport()));
    }

    @GetMapping("/parking-lot/csv")
    public ResponseEntity<ApiResponse<String>> generateCSVReport() {
        parkingLotReportService.generateCSVReport();
        return ResponseEntity.ok(new ApiResponse<>(true, "CSV report generated successfully", "C:/Users/log/report.csv"));
    }

    @GetMapping("/parking-lot/pdf")
    public ResponseEntity<ApiResponse<String>> generatePDFReport() {
        parkingLotReportService.generatePDFReport();
        return ResponseEntity.ok(new ApiResponse<>(true, "PDF report generated successfully", "C:/Users/log/report.pdf"));
    }

    @GetMapping("/parking-lot/daily")
    public ResponseEntity<ApiResponse<String>> generateDailyReports() {
        parkingLotReportService.generateDailyReports();
        return ResponseEntity.ok(new ApiResponse<>(true, "Daily report generated successfully", "C:/Users/log/daily-report.pdf"));
    }


    @GetMapping("/{id}/utilization")
    public ResponseEntity<ParkingLotUtilizationStatsResponse> getUtilizationStats(@PathVariable Long id) {
        ParkingLotUtilizationStatsResponse stats = parkingLotReportService.getUtilizationStats(id);
        return ResponseEntity.ok(stats);
    }
}
