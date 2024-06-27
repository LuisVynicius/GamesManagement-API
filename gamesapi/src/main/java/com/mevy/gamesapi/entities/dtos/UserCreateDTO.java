package com.mevy.gamesapi.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
    
        @NotBlank(message = "Username must have at least one character. ")
        @Size(
            message = "Username must have between 3-20 characters. ",
            min = 3,
            max = 25
        )
        String username,
        
        @NotBlank(message = "Password must have at least one character. ")
        @Size(
            message = "Password must have between 3-20 characters. ",
            min = 3,
            max = 25
        )
        String password,
        
        @NotBlank(message = "Email must have at least 10 characters. ")
        @Size(
            message = "Email must be valid. ",
            min = 10
        )
        String email
    ) {

}
