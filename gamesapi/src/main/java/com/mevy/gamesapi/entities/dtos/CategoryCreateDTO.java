package com.mevy.gamesapi.entities.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateDTO(
        
        @NotBlank(message = "Name must have at least one character. ")
        @Size(message = "Category name must not exceed 30 characters.", max = 30)
        String name
    ) {
        
}
