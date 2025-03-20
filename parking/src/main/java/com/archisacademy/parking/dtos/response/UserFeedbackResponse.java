package com.archisacademy.parking.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackResponse {

    private Long userId;

    private String feedbackText;

    private Timestamp createdAt;

}
