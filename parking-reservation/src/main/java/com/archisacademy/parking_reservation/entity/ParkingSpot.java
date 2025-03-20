package com.archisacademy.parking_reservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parking_spots")
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parking_spot_type", nullable = false)
    private String parkingSpotType;

    @Column(name = "is_spot_available", updatable = true)
    private Boolean availability = true;

    @Column(name = "parking_spot_location", nullable = false, updatable = false)
    private String parkingSpotLocation;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;
}
