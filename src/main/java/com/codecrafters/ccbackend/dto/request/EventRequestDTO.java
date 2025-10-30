package com.codecrafters.ccbackend.dto.request;

import com.codecrafters.ccbackend.entity.Event.EventCategory;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequestDTO {

    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer maxAttendees;
    private String location;
    private EventCategory category;
    private String imageUrl;
    private Long userId;
}
