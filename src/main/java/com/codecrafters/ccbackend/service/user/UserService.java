package com.codecrafters.ccbackend.service.user;

import java.util.List;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.entity.User;

public interface UserService {
    UserResponseDTO addUser(UserRequestDTO user);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, User user);
    void deleteUser(Long id);
}
