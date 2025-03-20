package com.archisacademy.parking.repositories;

import com.archisacademy.parking.model.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {

}
