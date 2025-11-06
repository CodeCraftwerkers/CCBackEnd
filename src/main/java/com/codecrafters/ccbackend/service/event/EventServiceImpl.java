package com.codecrafters.ccbackend.service.event;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.*;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.User;
import com.codecrafters.ccbackend.mapper.EventMapper;
import com.codecrafters.ccbackend.repository.EventRepository;
import com.codecrafters.ccbackend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Event event = eventMapper.toEntity(dto, creator);
        event = eventRepository.save(event);
        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponseDTO updateEvent(Long eventId, EventRequestDTO dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        eventMapper.updateEntityFromRequest(dto, event);
        event = eventRepository.save(event);
        return eventMapper.toResponse(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new RuntimeException("Evento no encontrado");
        }
        eventRepository.deleteById(eventId);
    }

    @Override
    public EventResponseDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        return eventMapper.toResponse(event);
    }

    @Override
    public Page<EventResponseDTO> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDateTime").ascending());
        return eventRepository.findAll(pageable).map(eventMapper::toResponse);
    }

    @Override
    public Page<EventResponseDTO> filterEvents(String title, String username, String categoryStr, String timeRange,
            LocalDateTime start,
            LocalDateTime end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDateTime").ascending());
        Page<Event> events = Page.empty(pageable);

        if (StringUtils.hasText(categoryStr)) {
            try {
                Event.EventCategory category = Event.EventCategory.valueOf(categoryStr.toUpperCase());
                events = eventRepository.findByCategory(category, pageable);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Categoria invalida: " + categoryStr);
            }
        }

        else if (StringUtils.hasText(title)) {
            events = eventRepository.findByTitleContainingIgnoreCase(title, pageable);
        }

        else if (StringUtils.hasText(username)) {
            events = eventRepository.findByUserUsername(username, pageable);
        }

        else if (StringUtils.hasText(timeRange)) {

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startRange;
            LocalDateTime endRange;

            switch (timeRange.toLowerCase()) {
                case "today" -> {
                    startRange = now.toLocalDate().atStartOfDay();
                    endRange = startRange.plusDays(1);
                }
                case "week" -> {
                    startRange = now.with(java.time.DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
                    endRange = startRange.plusDays(7);
                }
                case "month" -> {
                    startRange = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
                    endRange = startRange.plusMonths(1);
                }
                default -> throw new RuntimeException("Invalido timeRange. Usar: today, week, month");
            }

            events = eventRepository.findByStartDateTimeBetween(startRange, endRange, pageable);
        }

        else {
            events = eventRepository.findAll(pageable);
        }

        return events.map(eventMapper::toResponse);
    }

    @Override
    public EventResponseDTO signUp(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (event.getAttendees().contains(user)) {
            throw new RuntimeException("Usuario ya apuntado a este evento");
        }

        if (event.getMaxAttendees() != null &&
                event.getAttendees().size() >= event.getMaxAttendees()) {
            throw new RuntimeException("Este evento está lleno");
        }

        user.getSignedUpEvents().add(event);
        event.getAttendees().add(user);

        userRepository.save(user);

        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponseDTO unSign(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!event.getAttendees().contains(user)) {
            throw new RuntimeException("Usuario no apuntado a este evento");
        }

        user.getSignedUpEvents().remove(event);
        event.getAttendees().remove(user);

        userRepository.save(user);

        return eventMapper.toResponse(event);
    }

    @Override
    public List<UserResponseDTO> getAttendees(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        return event.getAttendees()
                .stream()
                .map(this::userToUserResponseDTO)
                .toList();
    }

    private UserResponseDTO userToUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    @Override
    public Page<EventResponseDTO> getEventsCreatedByUsername(String identifier, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findByEmail(identifier)
            .orElseGet(() -> userRepository.findByUsername(identifier)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

    Page<Event> events = eventRepository.findByUserUsername(user.getUsername(), pageable);
    return events.map(eventMapper::toResponse);
    }
}