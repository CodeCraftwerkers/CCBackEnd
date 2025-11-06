package com.codecrafters.ccbackend.dto.response;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username;
    private String email;
}
