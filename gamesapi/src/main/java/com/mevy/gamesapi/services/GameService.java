package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.Game;
import com.mevy.gamesapi.entities.dtos.GameCreateDTO;
import com.mevy.gamesapi.entities.dtos.GameUpdateDTO;
import com.mevy.gamesapi.repositories.GameRepository;
import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<Game> findAll() {
        List<Game> games = gameRepository.findAll();
        return games;
    }

    @Transactional(readOnly = true)
    public Game findByName(String name) {
        Game game = gameRepository.findByName(name).orElseThrow(
            () -> new ResourceNotFound(Game.class, name)
        );
        return game;
    }

    @Transactional(readOnly = true)
    public Game findById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(
            () -> new ResourceNotFound(Game.class, id)
        );
        return game;
    }

    public Game create(Game game) {
        if (gameRepository.existsByName(game.getName())) {
            throw new DatabaseIntegrityException("Game name already in use. ");
        }

        if (Objects.isNull(game.getDisabled())) {
            game.setDisabled(false);
        }

        game = gameRepository.save(game);
        return game;
    }

    public void delete(Long id) {
        findById(id);
        try {
            gameRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Database integrity violation error. ");
        }
    }

    public void update(Game newGame) {
        try{
            Game game = gameRepository.getReferenceById(newGame.getId());
            updateData(game, newGame);
            gameRepository.save(game);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(Game.class, newGame.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Game name already in use. ");
        }
    }

    private void updateData(Game game, Game newGame) {
        game.setName       ((Objects.nonNull(newGame.getName()))        ? newGame.getName()        : game.getName());
        game.setDescription((Objects.nonNull(newGame.getDescription())) ? newGame.getDescription() : game.getDescription());
        game.setAgeGroup   ((Objects.nonNull(newGame.getAgeGroup()))    ? newGame.getAgeGroup()    : game.getAgeGroup());
        game.setPrice      ((Objects.nonNull(newGame.getPrice()))       ? newGame.getPrice()       : game.getPrice());
        game.setDisabled   ((Objects.nonNull(newGame.getDisabled()))    ? newGame.getDisabled()    : game.getDisabled());
    }

    public Game fromDTO(GameCreateDTO gameCreateDTO) {
        Game game = new Game(
                null,
                gameCreateDTO.name(),
                gameCreateDTO.price(),
                gameCreateDTO.description(),
                gameCreateDTO.date(),
                gameCreateDTO.ageGroup(),
                gameCreateDTO.disabled()
        );
        return game;
    }

    public Game fromDTO(GameUpdateDTO gameUpdateDTO) {
        Game game = new Game(
                gameUpdateDTO.id(),
                gameUpdateDTO.name(),
                gameUpdateDTO.price(),
                gameUpdateDTO.description(),
                null,
                gameUpdateDTO.ageGroup(),
                gameUpdateDTO.disabled()
        );
        return game;
    }

}
