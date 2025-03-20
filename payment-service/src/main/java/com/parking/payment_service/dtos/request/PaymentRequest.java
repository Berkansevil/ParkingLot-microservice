package com.parking.payment_service.dtos.request;

import com.parking.payment_service.model.Payment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private Long reservationId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Payment method cannot be null")
    @Size(min = 1, message = "Payment method cannot be empty")
    private String paymentMethod;

    @NotNull(message = "Payment status cannot be null")
    private Payment.PaymentStatus paymentStatus;

}
