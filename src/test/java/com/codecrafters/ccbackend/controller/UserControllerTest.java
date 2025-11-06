package com.codecrafters.ccbackend.controller;

import com.codecrafters.ccbackend.dto.request.UserRequestDTO;
import com.codecrafters.ccbackend.dto.response.UserResponseDTO;
import com.codecrafters.ccbackend.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UserService userService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void createUser_shouldReturn201_andBody() throws Exception {
                UserRequestDTO req = new UserRequestDTO();
                req.setUsername("bob");
                req.setEmail("bob@example.com");
                req.setPassword("secret");

                UserResponseDTO resp = UserResponseDTO.builder()
                                .id(10L).username("bob").email("bob@example.com").build();

                Mockito.when(userService.addUser(Mockito.any(UserRequestDTO.class))).thenReturn(resp);

                mockMvc.perform(post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id", is(10)))
                                .andExpect(jsonPath("$.username", is("bob")))
                                .andExpect(jsonPath("$.email", is("bob@example.com")));
        }

        @Test
        void getAllUsers_shouldReturn200_andList() throws Exception {
                List<UserResponseDTO> list = List.of(
                                UserResponseDTO.builder().id(1L).username("alice").email("alice@example.com").build(),
                                UserResponseDTO.builder().id(2L).username("bob").email("bob@example.com").build());
                Mockito.when(userService.getAllUsers()).thenReturn(list);

                mockMvc.perform(get("/api/v1/users"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].username", is("alice")))
                                .andExpect(jsonPath("$[1].email", is("bob@example.com")));
        }

        @Test
        void getUserById_shouldReturn200_andUser() throws Exception {
                UserResponseDTO resp = UserResponseDTO.builder()
                                .id(1L).username("alice").email("alice@example.com").build();
                Mockito.when(userService.getUserById(1L)).thenReturn(resp);

                mockMvc.perform(get("/api/v1/users/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id", is(1)))
                                .andExpect(jsonPath("$.username", is("alice")));
        }
}
