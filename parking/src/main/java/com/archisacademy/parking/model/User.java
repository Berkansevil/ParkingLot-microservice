package com.archisacademy.parking.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "User ID", example = "1", required = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    @Schema(name = "Name", example = "John Doe", required = true, nullable = false)
    private String name;

    @Schema(name = "Username", example = "johndoe", required = true, nullable = false)
    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "password")
    @Schema(name = "Password", example = "password", required = true, nullable = false)
    private String password;

    @Column(name="email")
    @Schema(name = "Email", example = "johndoe@gmail.com", required = true, nullable = false)
    private String email;

    @Column(name = "phone_number")
    @Schema(name = "Phone Number", example = "+905323567890", required = true, nullable = false)
    private String phoneNumber;

    @Column(name = "active")
    @Schema(name = "Active", example = "true", required = true, nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserFeedback> userFeedbacks;

}
