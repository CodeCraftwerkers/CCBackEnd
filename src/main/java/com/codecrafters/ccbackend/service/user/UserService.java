package com.codecrafters.ccbackend.service.user;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.entity.User;

public interface UserService {
    ResponseEntity<UserResponseDTO> addUser(UserRequestDTO user);
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> getUserById(Long id);
    ResponseEntity<User> updateUser(Long id, User user);
    ResponseEntity<Void> deleteUser(Long id);
}
