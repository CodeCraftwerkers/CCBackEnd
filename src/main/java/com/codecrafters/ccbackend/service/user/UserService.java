package com.codecrafters.ccbackend.service.user;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.dto.response.UserUpdateDTO;
import com.codecrafters.ccbackend.entity.User;

public interface UserService {
    UserResponseDTO addUser(UserRequestDTO user);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
   // UserResponseDTO updateUser(Long id, User user);
    void deleteUser(Long id);
    //UserDetails loadUserByUsername(String name);
    UserDetails loadUserByEmail(String name);
    User findByEmail(String email);
    UserResponseDTO toResponse(User user);
    UserResponseDTO updateUser(Long id, UserUpdateDTO dto);

    void changePassword(String email, String oldPassword, String newPassword);
}


