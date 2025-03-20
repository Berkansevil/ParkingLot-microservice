package com.archisacademy.email_service.services;

import com.archisacademy.email_service.ApiResponse.ApiResponse;
import com.archisacademy.email_service.dtos.request.SendEmailRequest;
import com.archisacademy.email_service.dtos.request.SendTemplateEmailRequest;
import com.archisacademy.email_service.dtos.response.EmailTemplateResponse;
import com.archisacademy.email_service.dtos.response.SendEmailResponse;

public interface SendEmailService {

    ApiResponse<SendEmailResponse> sendEmail(SendEmailRequest request);

    ApiResponse<EmailTemplateResponse> SendEmailWithTemplate(SendTemplateEmailRequest templateEmailRequest);

}
