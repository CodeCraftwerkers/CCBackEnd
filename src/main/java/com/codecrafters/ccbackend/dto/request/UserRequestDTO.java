package com.codecrafters.ccbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {    
    @NotBlank(message = "Nombre no puede estar vacío")
    private String username;

    @NotBlank(message = "Email no puede estar vacío")
    @Email(message = "Email debe ser válido")
    private String email;

    private String password;
    
}
