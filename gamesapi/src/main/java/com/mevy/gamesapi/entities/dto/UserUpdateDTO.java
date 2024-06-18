package com.mevy.gamesapi.entities.dto;

import com.mevy.gamesapi.entities.User;

public record UserUpdateDTO(Long id, String username, String password) {
    
    public User toUser() {
        User user = new User(id, username, password, null);
        return user;
    }

}
