package com.archisacademy.report_generation_service.repositories;

import com.archisacademy.report_generation_service.model.ParkingLotReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotReportRepository extends JpaRepository<ParkingLotReport, Long> {
}
