package com.codecrafters.ccbackend.service.event;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;

import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

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
            String timeRange,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size);
    EventResponseDTO signUp(Long eventId, Long userId);
    EventResponseDTO unSign(Long eventId, Long userId);
    List<UserResponseDTO> getAttendees(Long eventId);
    Page<EventResponseDTO> getEventsCreatedByUsername(String username, int page, int size);
    Page<EventResponseDTO> getEventsUserJoined(String emailOrUsername, int page, int size);

}
