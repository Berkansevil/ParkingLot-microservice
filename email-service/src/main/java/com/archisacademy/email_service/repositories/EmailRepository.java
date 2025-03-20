package com.archisacademy.email_service.repositories;

import com.archisacademy.email_service.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {

}
