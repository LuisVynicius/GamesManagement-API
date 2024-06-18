package com.mevy.gamesapi.entities.dto;

import com.mevy.gamesapi.entities.Category;

public record CategoryCreateDTO(String name) {
    
    public Category toCategory() {
        Category category = new Category(null, name);
        return category;
    }

}
