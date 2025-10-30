package com.codecrafters.ccbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codecrafters.ccbackend.entity.Event;

public interface EventRepository extends JpaRepository <Event, Long>  {

    
    
}
