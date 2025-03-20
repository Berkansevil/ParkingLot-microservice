package com.archisacademy.stripeservice.repositories;


import com.archisacademy.stripeservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripePaymentRepository extends JpaRepository<Payment, Long> {
}
