package com.archisacademy.parking.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    @NotNull(message = "User id cannot be null")
    private Long userId;
    @NotNull(message = "Vehicle id cannot be null")
    private Long vehicleId;
    @NotNull(message = "Parking spot id cannot be null")
    private Long parkingSpotId;
    @NotNull(message = "Chek in date must be entered")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Check in date must be in the format YYYY-MM-DD")
    private LocalDate checkInDate;
    @NotNull(message = "Check out date must be entered")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Check out date must be in the format YYYY-MM-DD")
    private LocalDate checkOutDate;
}
