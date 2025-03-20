package com.archisacademy.report_generation_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class ParkingLotUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDateTime timestamp;
    private Long numberOfVehicles;

    @ManyToOne
    @JoinColumn(name = "parking_lot_report_id")
    private ParkingLotReport parkingLotReport;
}