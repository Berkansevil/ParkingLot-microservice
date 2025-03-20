package com.archisacademy.parking.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedbackRequest {

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Feedback text cannot be null")
    private String feedbackText;

}
