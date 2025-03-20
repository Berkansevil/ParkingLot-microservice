package com.archisacademy.email_service.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequest {

    @Email
    @NotNull(message = "Recipient e-mail address is mandatory!")
    private String toEmail;

    @NotNull(message = "Mail subject is mandatory!")
    private String subject;

    @NotNull(message = "Mail body is mandatory!")
    private String body;

    private String attachmentPath;

}
