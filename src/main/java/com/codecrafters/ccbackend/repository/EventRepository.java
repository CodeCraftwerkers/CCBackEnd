package com.codecrafters.ccbackend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.mapper.EventMapper;

public interface EventRepository extends JpaRepository<Event, Long> {

}
