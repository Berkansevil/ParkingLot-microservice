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
public class ReviewResponse {
    private Long id;
    private Long parkingLotId;
    private String username;
    private String comment;
    private int rating;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
