package com.codecrafters.ccbackend.mapper;

import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserSummaryDTO;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.User;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "attendees", ignore = true)

    Event toEntity(EventRequestDTO request, User user);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "attendees", expression = "java(mapAttendees(event.getAttendees()))")
    EventResponseDTO toResponse(Event event);

    UserResponseDTO userToUserResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "attendees", ignore = true)
    void updateEntityFromRequest(EventRequestDTO request, @MappingTarget Event event);

    default Set<UserSummaryDTO> mapAttendees(Set<User> attendees) {
        if (attendees == null)
            return Set.of();
        return attendees.stream()
                .map(u -> new UserSummaryDTO(u.getId(), u.getUsername()))
                .collect(Collectors.toSet());

    }
}