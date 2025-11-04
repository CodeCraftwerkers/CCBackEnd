package com.codecrafters.ccbackend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.service.event.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor

public class EventController {
    private final EventService eventService;

    @PostMapping
    public EventResponseDTO createEvent(@Valid @RequestBody EventRequestDTO event) {
        return eventService.createEvent(event);
    }

    @GetMapping
    public Page<EventResponseDTO> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        return eventService.getAllEvents(page, size);
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/filter")
    public Page<EventResponseDTO> filterEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String timeRange,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        return eventService.filterEvents(title, username, category, timeRange, start, end, page, size);
    }

    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(@PathVariable Long id, @RequestBody EventRequestDTO dto) {
        return eventService.updateEvent(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/signup/{id}")
    public EventResponseDTO signUp(@PathVariable Long id) {
        return eventService.signUp(id);
    }

    @DeleteMapping("/signup/{id}")
    public EventResponseDTO unSign(@PathVariable Long id) {
        return eventService.unSign(id);
    }

    @GetMapping("/{id}/attendees")
    public ResponseEntity<List<UserResponseDTO>> getAttendees(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getAttendees(id));
    }
}
