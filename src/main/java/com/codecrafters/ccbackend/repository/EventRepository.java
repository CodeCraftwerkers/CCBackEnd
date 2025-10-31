package com.codecrafters.ccbackend.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.Event.EventCategory;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByCategory(EventCategory category, Pageable pageable);
    Page<Event> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Event> findByUserUsername(String username, Pageable pageable);
    Page<Event> findByStartDateTime(LocalDateTime start, Pageable pageable);

}
