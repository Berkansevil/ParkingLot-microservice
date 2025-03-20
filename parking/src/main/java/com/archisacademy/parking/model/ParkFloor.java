package com.archisacademy.parking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "park_floor")
public class ParkFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private int floor;
    private int capacity;
    private String floorBlock;

    @OneToOne
    private ParkingLot parkingLot;
}
