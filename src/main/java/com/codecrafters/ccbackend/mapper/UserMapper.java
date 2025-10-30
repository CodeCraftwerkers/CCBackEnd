package com.codecrafters.ccbackend.mapper;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO request);    
    
    UserResponseDTO toResponse(User user);
    
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UserRequestDTO request, @MappingTarget User user);
}