package com.archisacademy.parking.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private Long parkingLotId;
    private String username;
    private String comment;
    private int rating;
}
