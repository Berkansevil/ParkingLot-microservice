package com.archisacademy.stripeservice.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StripePaymentRequest {
    private Long reservationId;
    public enum Currency {
        EUR, USD;
    }
    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;
    @NotNull(message = "Payment method cannot be null")
    private String paymentMethod;
    private Currency currency;

}