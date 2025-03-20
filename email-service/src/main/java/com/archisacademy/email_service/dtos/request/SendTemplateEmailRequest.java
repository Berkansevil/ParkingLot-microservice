package com.archisacademy.email_service.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendTemplateEmailRequest {

    @Email
    @NotNull(message = "Recipient e-mail address is mandatory!")
    private String to;

    @NotNull(message = "Mail subject is mandatory!")
    private String subject;

    @NotNull(message = "Mail template is mandatory!")
    private String template;

    @NotNull(message = "Mail model is mandatory!")
    private Map<String, Object> model;

}
