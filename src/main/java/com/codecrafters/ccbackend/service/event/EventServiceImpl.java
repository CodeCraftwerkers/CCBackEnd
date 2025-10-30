package com.codecrafters.ccbackend.service.event;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.Event.EventCategory;
import com.codecrafters.ccbackend.entity.User;
import com.codecrafters.ccbackend.mapper.EventMapper;
import com.codecrafters.ccbackend.repository.EventRepository;
import com.codecrafters.ccbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<EventResponseDTO> filterEvents(
            String title,
            String username,
            String categoryStr,
            LocalDateTime start,
            LocalDateTime end,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startDateTime").ascending());
        EventCategory category = null;

        if (StringUtils.hasText(categoryStr)) {
            try {
                category = EventCategory.valueOf(categoryStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid category");
            }
        }

        return eventRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (StringUtils.hasText(title)) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(username)) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("user").get("username"), username));
            }

            if (category != null) {
                predicate = cb.and(predicate, cb.equal(root.get("category"), category));
            }

            if (start != null && end != null) {
                predicate = cb.and(predicate, cb.between(root.get("startDateTime"), start, end));
            }

            return predicate;
        }, pageable).map(eventMapper::toResponse);
    }
}