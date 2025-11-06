package com.codecrafters.ccbackend.mapper;

import org.mapstruct.*;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserSummaryDTO;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.User;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "attendees", ignore = true)
    Event toEntity(EventRequestDTO request, User user);

    @Mapping(target = "user")
    @Mapping(target = "attendees")
    EventResponseDTO toResponse(Event event);

    UserSummaryDTO userToUserSummaryDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)

    @Mapping(target = "attendees", ignore = true)
    void updateEntityFromRequest(EventRequestDTO request, @MappingTarget Event event);

  }