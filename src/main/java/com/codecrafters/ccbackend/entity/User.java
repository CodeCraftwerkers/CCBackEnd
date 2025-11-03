package com.codecrafters.ccbackend.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

@ManyToMany(mappedBy = "attendees")
@Builder.Default
private Set<Event> signedUpEvents = new HashSet<>();
    
}
