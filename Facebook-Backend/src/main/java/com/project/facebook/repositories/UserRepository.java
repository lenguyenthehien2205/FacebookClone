package com.project.facebook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.facebook.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
