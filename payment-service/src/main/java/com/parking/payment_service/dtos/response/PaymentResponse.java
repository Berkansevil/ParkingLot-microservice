package com.parking.payment_service.dtos.response;

import com.parking.payment_service.model.Payment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private Long reservationId;
    private Double amount;
    private String paymentMethod;
    private Payment.PaymentStatus paymentStatus;
    private LocalDateTime timestamp;
}
