package com.codecrafters.ccbackend.service;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.entity.User;
import com.codecrafters.ccbackend.mapper.UserMapper;
import com.codecrafters.ccbackend.repository.UserRepository;
import com.codecrafters.ccbackend.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequestDTO request;
    private User entityToSave;
    private User entitySaved;
    private UserResponseDTO response;

    @BeforeEach
    void setupData() {
        request = new UserRequestDTO();
        request.setUsername("alice");
        request.setEmail("alice@example.com");
        request.setPassword("Password123!");

        entityToSave = User.builder()
                .username("alice")
                .email("alice@example.com")
                .password("Password123!")
                .build();

        entitySaved = User.builder()
                .id(1L)
                .username("alice")
                .email("alice@example.com")
                .password("Password123!")
                .build();

        response = UserResponseDTO.builder()
                .id(1L)
                .username("alice")
                .email("alice@example.com")
                .build();
    }

    @Test
    void addUser_shouldMapSaveAndReturnResponse() {
        when(userMapper.toEntity(request)).thenReturn(entityToSave);
        when(userRepository.save(entityToSave)).thenReturn(entitySaved);
        when(userMapper.toResponse(entitySaved)).thenReturn(response);

        UserResponseDTO out = userService.addUser(request);

        assertNotNull(out);
        assertEquals(1L, out.getId());
        assertEquals("alice", out.getUsername());
        assertEquals("alice@example.com", out.getEmail());

        verify(userMapper).toEntity(request);
        verify(userRepository).save(entityToSave);
        verify(userMapper).toResponse(entitySaved);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    void getUserById_whenFound_returnsResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(entitySaved));
        when(userMapper.toResponse(entitySaved)).thenReturn(response);

        UserResponseDTO out = userService.getUserById(1L);

        assertEquals(1L, out.getId());
        verify(userRepository).findById(1L);
        verify(userMapper).toResponse(entitySaved);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    void getUserById_whenNotFound_throws() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
        assertTrue(ex.getMessage().toLowerCase().contains("user not found"));

        verify(userRepository).findById(99L);
        verifyNoMoreInteractions(userRepository, userMapper);
    }
}
