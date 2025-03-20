package com.archisacademy.report_generation_service.services;

import com.archisacademy.report_generation_service.dtos.ParkingLotReportResponse;
import com.archisacademy.report_generation_service.dtos.ParkingLotUtilizationStatsResponse;
import com.archisacademy.report_generation_service.model.ParkingLotReport;

public interface ParkingLotReportService {
    ParkingLotReportResponse generateParkingLotReport();

    void generateCSVReport();

    void generatePDFReport();

    void generateDailyReports();

    ParkingLotUtilizationStatsResponse getUtilizationStats(Long parkingLotId);
}
