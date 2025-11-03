package com.codecrafters.ccbackend.service.event;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.User;
import com.codecrafters.ccbackend.mapper.EventMapper;
import com.codecrafters.ccbackend.repository.EventRepository;
import com.codecrafters.ccbackend.repository.UserRepository;

import jakarta.transaction.Transactional;
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
    @Transactional
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
                throw new RuntimeException("Invalid category: " + categoryStr);
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
                default -> throw new RuntimeException("Invalid timeRange. Use: today, week, month");
            }

            events = eventRepository.findByStartDateTimeBetween(startRange, endRange, pageable);
        }

        else {
            events = eventRepository.findAll(pageable);
        }

        return events.map(eventMapper::toResponse);
    }

    @Transactional
    @Override
    public void signup(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (eventRepository.countSignup(eventId, userId) > 0) {
            throw new RuntimeException("User already signed up to this event");
        }

        if (event.getAttendees().size() >= event.getMaxAttendees()) {
            throw new RuntimeException("Event is full");
        }

        event.getAttendees().add(user);
        eventRepository.save(event);
    }

    @Transactional
    @Override
    public void unsign(Long eventId, Long userId) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        eventRepository.deleteSignup(eventId, userId);
    }

}