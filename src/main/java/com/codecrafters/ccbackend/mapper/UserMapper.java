package com.codecrafters.ccbackend.mapper;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserUpdateDTO;
import com.codecrafters.ccbackend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO request);

    UserResponseDTO toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) 
    @Mapping(target = "signedUpEvents", ignore = true)
   // @Mapping(target = "createdEvents", ignore = true)
    void updateEntityFromRequest(UserUpdateDTO request, @MappingTarget User user);
}
