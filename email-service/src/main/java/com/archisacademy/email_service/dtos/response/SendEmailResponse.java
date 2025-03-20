package com.archisacademy.email_service.dtos.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailResponse {

    private String toEmail;

    private String subject;

    private String body;

    private String attachmentPath;

    private Timestamp sendDate;

}
