package com.mevy.gamesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.gamesapi.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
    
}
