package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.Game;
import com.mevy.gamesapi.repositories.GameRepository;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<Game> findAll() {
        List<Game> games = gameRepository.findAll();
        return games;
    }

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    public void update(Game newGame) {
        Game game = gameRepository.getReferenceById(newGame.getId());
        updateData(game, newGame);
        gameRepository.save(game);
    }

    private void updateData(Game game, Game newGame) {
        game.setName((Objects.nonNull(newGame.getName())) ? newGame.getName() : game.getName());
        game.setDescription((Objects.nonNull(newGame)) ? newGame.getDescription() : game.getDescription());
        game.setAgeGroup((Objects.nonNull(newGame.getAgeGroup())) ? newGame.getAgeGroup() : game.getAgeGroup());
        game.setPrice((Objects.nonNull(newGame.getPrice())) ? newGame.getPrice() : game.getPrice());
    }

}
