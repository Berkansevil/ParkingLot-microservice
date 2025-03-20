package com.parking.payment_service.services.Impl;


import com.parking.payment_service.apiresponse.ApiResponse;
import com.parking.payment_service.dtos.request.PaymentRequest;
import com.parking.payment_service.dtos.request.PaymentUpdateRequest;
import com.parking.payment_service.dtos.response.PaymentResponse;
import com.parking.payment_service.exception.PaymentNotFoundException;
import com.parking.payment_service.model.Payment;
import com.parking.payment_service.modelmapper.ModelMapperService;
import com.parking.payment_service.repositories.PaymentRepository;
import com.parking.payment_service.services.PaymentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapperService modelMapperService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, ModelMapperService modelMapperService) {
        this.paymentRepository = paymentRepository;
        this.modelMapperService = modelMapperService;
    }


    @Override
//    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public ApiResponse<PaymentResponse>  initiatePayment(PaymentRequest paymentRequest) {
        Payment payment = modelMapperService.request().map(paymentRequest, Payment.class);
        Payment savedPayment = paymentRepository.save(payment);
        PaymentResponse paymentResponse = modelMapperService.response().map(savedPayment, PaymentResponse.class);
        return new ApiResponse<>(true, "Payment initiated successfully.", paymentResponse);
    }

    @Override
    public ApiResponse<PaymentResponse> updatePayment(Long paymentId, PaymentUpdateRequest paymentUpdateRequest) {
        Payment payment = getPaymentById(paymentId);
        modelMapperService.request().map(paymentUpdateRequest, payment);
        Payment updatedPayment = paymentRepository.save(payment);
        PaymentResponse paymentResponse = modelMapperService.response().map(updatedPayment, PaymentResponse.class);
        paymentResponse.setTimestamp(LocalDateTime.now());
        return new ApiResponse<>(true, "Payment updated successfully.", paymentResponse);
    }

    @Override
    public ApiResponse<PaymentResponse> getPayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        PaymentResponse paymentResponse = modelMapperService.request().map(payment, PaymentResponse.class);
        paymentResponse.setTimestamp(LocalDateTime.now());
        return new ApiResponse<>(true, "Payment found.", paymentResponse);
    }


    @Override
    @Scheduled(cron = "0 0 3 * * ?")
    public ApiResponse<List<PaymentResponse>> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentResponse> paymentResponse = payments.stream()
                .map(payment -> modelMapperService.response().map(payment, PaymentResponse.class))
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Payments found.", paymentResponse);
    }

    @Override
    public ApiResponse<String> deletePayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        paymentRepository.delete(payment);
        return new ApiResponse<>(true, "Payment deleted successfully.", "Payment deleted successfully.");
    }

    private Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> {
                    throw new PaymentNotFoundException("Payment not found with ID: " + paymentId);
                }
        );
    }
}
