package com.archisacademy.parking.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_feedbacks")
public class UserFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "ID", example = "Feedback ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @Schema(name = "User ID", example = "1", required = true)
    private User user;

    @Column(name = "feedback_text", nullable = false)
    @Schema(name = "Feedback Text", example = "Harika kullanıcı", required = true)
    private String feedbackText;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Schema(name = "created_at", example = "2025-03-12T14:30:00.000Z")
    private Timestamp createdAt;

}
