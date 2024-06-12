package com.mevy.gamesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.gamesapi.entities.Game;

public interface GameRepositorie extends JpaRepository<Game, Long>{
    
}
