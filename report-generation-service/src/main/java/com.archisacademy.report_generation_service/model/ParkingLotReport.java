package com.archisacademy.report_generation_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private String parkingLotName;
    private String location;
    private Long totalCapacity;
    private Long currentUsage;
    private Double revenue;
    @OneToMany(mappedBy = "parkingLotReport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ParkingLotUsage> usageHistory;

}