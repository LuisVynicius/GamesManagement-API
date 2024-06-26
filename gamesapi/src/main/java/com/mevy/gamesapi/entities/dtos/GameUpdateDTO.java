package com.mevy.gamesapi.entities.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GameUpdateDTO(
    
        @NotNull(message = "Id cannot be null. ")
        Long id,
        
        @NotBlank(message = "Name must have at least one character. ")
        @Size(
            message = "Name must have between 3-20 characters. ",
            min = 3,
            max = 25
        )
        String name,
        
        @Digits(
            message = "The price must be a number with up to 5 digits in the integer part and up to 2 digits in the decimal part. ",
            integer = 5,
            fraction = 2
        )
        Float price,
        
        @Size(
            message = "Description must not exceed 255 characters. ",
            max = 255
        )
        String description,
        
        @Min(
            message = "The age group must be a positive number or zero. ",
            value = 0
        )
        Short ageGroup,

        Boolean disabled
    ) {
    
}
