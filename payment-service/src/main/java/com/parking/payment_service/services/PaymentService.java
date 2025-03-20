package com.parking.payment_service.services;

import com.parking.payment_service.apiresponse.ApiResponse;
import com.parking.payment_service.dtos.request.PaymentRequest;
import com.parking.payment_service.dtos.request.PaymentUpdateRequest;
import com.parking.payment_service.dtos.response.PaymentResponse;

import java.util.List;

public interface PaymentService {
    // Method to process a new payment
    ApiResponse<PaymentResponse> initiatePayment(PaymentRequest paymentRequest);

    ApiResponse<PaymentResponse> updatePayment(Long paymentId, PaymentUpdateRequest paymentUpdateRequest);

    ApiResponse<PaymentResponse> getPayment(Long paymentId);

    ApiResponse<List<PaymentResponse>> getAllPayments();

    ApiResponse<String> deletePayment(Long paymentId);
}
