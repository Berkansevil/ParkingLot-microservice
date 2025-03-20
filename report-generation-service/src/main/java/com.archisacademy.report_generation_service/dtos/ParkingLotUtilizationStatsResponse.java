package com.archisacademy.report_generation_service.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotUtilizationStatsResponse {
    private String parkingLotName;
    private String location;
    private Long totalCapacity;
    private Long currentUsage;
    private Double revenue;
    private List<String> usageHistory;
    private Double occupancyRate;
    private String peakTime;
}