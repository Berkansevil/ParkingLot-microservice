package com.parking.payment_service.controllers;

import com.parking.payment_service.apiresponse.ApiResponse;
import com.parking.payment_service.dtos.request.PaymentRequest;
import com.parking.payment_service.dtos.request.PaymentUpdateRequest;
import com.parking.payment_service.dtos.response.PaymentResponse;
import com.parking.payment_service.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Initiate a payment", description = "Initiate a new payment")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "HTTP Status 201 Created" ,content = { @Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json") } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> initiatePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        ApiResponse<PaymentResponse> apiResponse = paymentService.initiatePayment(paymentRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{paymentId}")
    @Operation(summary = "Update a payment", description = "Update an existing payment")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json") } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(@PathVariable Long paymentId, @Valid @RequestBody PaymentUpdateRequest paymentUpdateRequest) {
        ApiResponse<PaymentResponse> apiResponse = paymentService.updatePayment(paymentId, paymentUpdateRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "Get a payment", description = "Get an existing payment")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json") } ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable Long paymentId) {
        ApiResponse<PaymentResponse> apiResponse = paymentService.getPayment(paymentId);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{paymentId}")
    @Operation(summary = "Delete a payment", description = "Delete an existing payment")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema()) } ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<String>> deletePayment(@PathVariable Long paymentId) {
        ApiResponse<String> apiResponse = paymentService.deletePayment(paymentId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all payments", description = "Get all existing payments")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK", content = { @Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json") } ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        ApiResponse<List<PaymentResponse>> apiResponse = paymentService.getAllPayments();
        return ResponseEntity.ok(apiResponse);
    }

}
