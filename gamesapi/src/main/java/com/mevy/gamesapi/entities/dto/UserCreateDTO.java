package com.mevy.gamesapi.entities.dto;

import com.mevy.gamesapi.entities.User;

public record UserCreateDTO(String username, String password, String email) {
    
    public User toUser() {
        User user = new User(null, username, password, email);
        return user;
    }

}
