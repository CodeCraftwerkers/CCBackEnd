package com.codecrafters.ccbackend.mapper;

import org.mapstruct.*;
import java.util.Locale;

import com.codecrafters.ccbackend.dto.request.EventRequestDTO;
import com.codecrafters.ccbackend.dto.response.EventResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.entity.Event;
import com.codecrafters.ccbackend.entity.User;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")

    Event toEntity(EventRequestDTO request, User user);
    
    @Mapping(target = "userId", source = "user")
    EventResponseDTO toResponse(Event event);
    UserResponseDTO userToUserResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(EventRequestDTO request, @MappingTarget Event event);

    default Locale.Category map(Event.EventCategory category) {
        return category == null ? null : Locale.Category.valueOf(category.name());
    }
}
