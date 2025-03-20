package com.archisacademy.parking_reservation.entity;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.CookieValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parking_reservation")
public class ParkingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Reservation ID", example = "1", required = true, nullable = false)
    private Long reservationId;
    @Column(name = "vehicle_id")
    @Schema(name = "Vehicle ID", example = "1", required = true, nullable = false)
    private Long vehicleId;
    @Column(name ="parking_spot_id")
    @Schema(name = "Parking Spot ID", example = "1", required = true, nullable = false)
    private Long parkingSpotId;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    @Schema(name = "Creation Timestamp", example = "2025-02-01T10:15:30", required = true, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "end_time", nullable = false)
    @Schema(name = "End Time", example = "2025-02-01T12:00:00", required = true, nullable = false)
    private LocalDateTime endTime;


}

