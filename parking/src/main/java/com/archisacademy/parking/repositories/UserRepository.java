package com.archisacademy.parking.repositories;

import com.archisacademy.parking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    List<User>findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);


}
