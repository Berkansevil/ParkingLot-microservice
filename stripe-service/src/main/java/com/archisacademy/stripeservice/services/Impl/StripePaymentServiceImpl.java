package com.archisacademy.stripeservice.services.Impl;


import com.archisacademy.stripeservice.dtos.request.StripePaymentRequest;
import com.archisacademy.stripeservice.dtos.response.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripePaymentServiceImpl {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    public StripeResponse checkout(StripePaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = secretKey;
        SessionCreateParams.LineItem.PriceData priceData = new SessionCreateParams.LineItem.PriceData.Builder()
                .setCurrency(paymentRequest.getCurrency() == null ? "USD" : String.valueOf(paymentRequest.getCurrency()))
                .setUnitAmountDecimal(paymentRequest.getAmount().multiply(new BigDecimal("100")))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Parking fee for reservation Id:" + paymentRequest.getReservationId())
                                .build()
                )
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://example.com/success")
                .setCancelUrl("https://example.com/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(priceData)
                                .setQuantity(1L)
                                .build()
                )
                .build();
        Session session = Session.create(params);
        return new StripeResponse("Success","Payment initiated", session.getId(), session.getUrl());
    }
}