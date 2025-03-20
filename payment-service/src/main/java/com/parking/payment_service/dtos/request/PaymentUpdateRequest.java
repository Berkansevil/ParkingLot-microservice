package com.parking.payment_service.dtos.request;

import com.parking.payment_service.model.Payment;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentUpdateRequest {
    private String paymentMethod;
    private Payment.PaymentStatus paymentStatus;
}
