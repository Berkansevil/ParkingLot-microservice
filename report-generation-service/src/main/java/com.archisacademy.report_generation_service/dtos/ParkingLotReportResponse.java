package com.archisacademy.report_generation_service.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotReportResponse {

    private String parkingLotName;
    private String location;
    private Long totalCapacity;
    private Long currentUsage;
    private Double revenue;
    private List<String> usageHistory;

}
