package com.codecrafters.ccbackend.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.Event.EventCategory;

import jakarta.transaction.Transactional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByCategory(EventCategory category, Pageable pageable);
    Page<Event> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Event> findByUserUsername(String username, Pageable pageable);
    Page<Event> findByStartDateTime(LocalDateTime start, Pageable pageable);
    Page<Event> findByStartDateTimeBetween(LocalDateTime startRange, LocalDateTime endRange, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_signups WHERE event_id = :eventId AND user_id = :userId", nativeQuery = true)
    void deleteSignup(Long eventId, Long userId);

    @Query(value = "SELECT COUNT(*) FROM event_signups WHERE event_id = :eventId AND user_id = :userId", nativeQuery = true)
    Long countSignup(Long eventId, Long userId);
}
