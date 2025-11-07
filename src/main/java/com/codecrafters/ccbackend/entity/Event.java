package com.codecrafters.ccbackend.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
  
    @Column(name = "max_attendees")
    private Integer maxAttendees;

    private String location;

    @Enumerated(EnumType.STRING)
    private EventCategory category;

    private String imageUrl;

    public enum EventCategory {
        ONLINE,
        PRESENCIAL
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToMany(mappedBy = "signedUpEvents")
    @Builder.Default
    private Set<User> attendees = new HashSet<>();
}
