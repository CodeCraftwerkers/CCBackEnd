package com.codecrafters.ccbackend.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.codecrafters.ccbackend.entity.Event.EventCategory;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer maxAttendees;
    private String location;
    private EventCategory category;
    private String imageUrl;
    private UserResponseDTO userId;
    private Set<UserSummaryDTO> attendees;
}
