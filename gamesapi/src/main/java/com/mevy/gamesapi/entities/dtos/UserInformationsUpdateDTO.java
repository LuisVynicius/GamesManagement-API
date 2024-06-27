package com.mevy.gamesapi.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInformationsUpdateDTO(

        @NotBlank(message = "Name cannot be null and must have at least one character. ")
        @Size(
            message = "name must not exceed 30 characters. ",
            max = 30
        )
        String name,
        
        
        @NotBlank(message = "Last Name cannot be null and must have at least one character. ")
        @Size(
            message = "Last Name must not exceed 30 characters. ",
            max = 30
        )
        String lastName
    ){
    
}
