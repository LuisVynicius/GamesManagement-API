package com.mevy.gamesapi.entities.dto;

import com.mevy.gamesapi.entities.Game;

public record GameUpdateDTO(Long id, String name, Float price, String description, Short ageGroup) {

    public Game toGame() {
        Game game = new Game(id, name, price, description, null, ageGroup);
        return game;
    }
    
}
