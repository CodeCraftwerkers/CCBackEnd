package com.codecrafters.ccbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codecrafters.ccbackend.entity.User;

public interface UserRepository extends JpaRepository <User, Long> {
    
}
