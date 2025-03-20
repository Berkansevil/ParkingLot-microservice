package com.archisacademy.email_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "to_email", nullable = false)
    @Schema(name = "Receiver Email", example = "test@mail.com", required = true)
    private String toEmail;

    @Column(name = "subject", nullable = false)
    @Schema(name = "Email Subject", example = "Merhaba", required = true)
    private String subject;

    @Column(name = "body")
    @Schema(name = "Email Body", example = "Naber")
    private String body;

    @Column(name = "attachment_path")
    @Schema(name = "Email Attachment Path", example = "C://users/resim.jpg")
    private String attachmentPath;

    @Column(name = "template")
    @Schema(name = "Email Template Name", example = "reservation_confirmation")
    private String template;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "model", columnDefinition = "jsonb")
    private Map<String, String> model;

    @CreationTimestamp
    @Column(name = "send_date", nullable = false, updatable = false)
    @Schema(name = "Email Sending Date", example = "2025-02-04 14:25:30")
    private Timestamp sendDate;

}
