package com.archisacademy.parking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingHistoryResponse {

    private Long id;
    private Long vehicleId;
    private Long parkingSpotId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
