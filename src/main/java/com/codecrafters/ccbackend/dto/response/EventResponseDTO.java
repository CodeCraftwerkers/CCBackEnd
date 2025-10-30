package com.codecrafters.ccbackend.dto.response;

import java.time.LocalDateTime;
import java.util.Locale.Category;

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
    private Category category;
    private String imageUrl;
    private UserResponseDTO userId;
}
