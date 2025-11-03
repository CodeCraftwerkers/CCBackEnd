package com.codecrafters.ccbackend.mapper;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO request);

    UserResponseDTO toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(UserRequestDTO request, @MappingTarget User user);
}
