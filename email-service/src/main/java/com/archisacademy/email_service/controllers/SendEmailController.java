package com.archisacademy.email_service.controllers;

import com.archisacademy.email_service.ApiResponse.ApiResponse;
import com.archisacademy.email_service.dtos.request.SendEmailRequest;
import com.archisacademy.email_service.dtos.request.SendTemplateEmailRequest;
import com.archisacademy.email_service.dtos.response.EmailTemplateResponse;
import com.archisacademy.email_service.dtos.response.SendEmailResponse;
import com.archisacademy.email_service.services.SendEmailService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
public class SendEmailController {

    private final SendEmailService sendEmailService;

    public SendEmailController(SendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("/send")
    @Operation(summary = "Send mail", description = "Email sending process")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK" ,content = { @Content(schema = @Schema(implementation = SendEmailResponse.class), mediaType = "application/json") } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<SendEmailResponse>> sendEmail(@Valid @RequestBody SendEmailRequest request) {
        ApiResponse<SendEmailResponse> response = sendEmailService.sendEmail(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send/template")
    @Operation(summary = "Send mail with template", description = "Sending email with a ready-made template")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK" ,content = { @Content(schema = @Schema(implementation = EmailTemplateResponse.class), mediaType = "application/json") } ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 Bad Request", content = { @Content(schema = @Schema()) })
    })
    public ResponseEntity<ApiResponse<EmailTemplateResponse>> sendEmailWithTemplate(@Valid @RequestBody SendTemplateEmailRequest sendTemplateEmailRequest) {
        ApiResponse<EmailTemplateResponse> response = sendEmailService.SendEmailWithTemplate(sendTemplateEmailRequest);
        return ResponseEntity.ok(response);
    }

}
