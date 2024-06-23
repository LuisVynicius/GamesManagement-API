package com.mevy.gamesapi.entities.dtos;

import com.mevy.gamesapi.entities.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
    
        @NotNull(message = "Id cannot be null. ")
        Long id,

        @NotBlank(message = "Username must have at least one character. ")
        @Size(
            message = "Username must have between 3-20 characters",
            min = 3,
            max = 25
        )
        String username,
        
        @NotBlank(message = "Password must have at least five characters. ")
        @Size(
            message = "password must have between 3-20 characters",
            min = 3,
            max = 25
        )
        String password
    ) {
    
    public User toUser() {
        User user = new User(id, username, password, null);
        return user;
    }

}
