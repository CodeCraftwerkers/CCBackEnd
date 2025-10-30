package com.codecrafters.ccbackend.service.event;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.User;
import com.codecrafters.ccbackend.mapper.EventMapper;
import com.codecrafters.ccbackend.repository.EventRepository;
import com.codecrafters.ccbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponseDTO createEvent(EventRequestDTO dto) {
        User creator = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventMapper.toEntity(dto, creator);
        event = eventRepository.save(event);
        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponseDTO updateEvent(Long eventId, EventRequestDTO dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventMapper.updateEntityFromRequest(dto, event);
        event = eventRepository.save(event);
        return eventMapper.toResponse(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new RuntimeException("Event not found");
        }
        eventRepository.deleteById(eventId);
    }

    @Override
    public EventResponseDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return eventMapper.toResponse(event);
    }

    @Override
    public Page<EventResponseDTO> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDateTime").ascending());
        return eventRepository.findAll(pageable).map(eventMapper::toResponse);
    }

   
    @Override
    public Page<EventResponseDTO> filterEvents(String title, String username, String category, LocalDateTime start,
            LocalDateTime end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());
        if (StringUtils.hasText(category)) {
            try {
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid category");
            }
        }

        Page<Event> events = eventRepository.findAll(pageable);
        

        return events.map(eventMapper::toResponse);
    }


}