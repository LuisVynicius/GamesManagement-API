package com.mevy.gamesapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
    
    @Transactional(readOnly = true)
    Boolean existsByName(String name);

    @Transactional(readOnly = true)
    Optional<Game> findByName(String name);

}
