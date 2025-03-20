package com.archisacademy.stripeservice.controllers;

import com.archisacademy.stripeservice.dtos.request.StripePaymentRequest;
import com.archisacademy.stripeservice.dtos.response.StripeResponse;
import com.archisacademy.stripeservice.services.Impl.StripePaymentServiceImpl;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/stripe")
public class StripePaymentController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    private final StripePaymentServiceImpl stripeService;

    public StripePaymentController(StripePaymentServiceImpl stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody StripePaymentRequest paymentRequest) throws StripeException {
        StripeResponse stripeResponse = stripeService.checkout(paymentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }
}
