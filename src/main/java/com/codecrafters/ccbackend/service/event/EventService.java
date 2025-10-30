package com.codecrafters.ccbackend.service.event;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;

import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface EventService {

    EventResponseDTO createEvent(EventRequestDTO dto);
    EventResponseDTO updateEvent(Long eventId, EventRequestDTO dto);
    void deleteEvent(Long eventId);
    EventResponseDTO getEventById(Long eventId);
    Page<EventResponseDTO> getAllEvents(int page, int size);
    Page<EventResponseDTO> filterEvents(
            String title,
            String username,
            String category,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size);

}
