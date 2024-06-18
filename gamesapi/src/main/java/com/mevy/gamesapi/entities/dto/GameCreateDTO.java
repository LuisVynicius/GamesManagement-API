package com.mevy.gamesapi.entities.dto;

import java.time.Instant;

import com.mevy.gamesapi.entities.Game;

public record GameCreateDTO(String name, Float price, String description, Instant date, Short ageGroup) {

    public Game toGame() {
        Game game = new Game(null, name, price, description, date, ageGroup);
        return game;
    }

}
