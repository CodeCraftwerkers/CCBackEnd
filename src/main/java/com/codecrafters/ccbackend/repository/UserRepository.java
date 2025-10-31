package com.codecrafters.ccbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codecrafters.ccbackend.entity.User;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
}
