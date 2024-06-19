package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.Game;
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
    public Game findById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new ResourceNotFound(Game.class, id));
        return game;
    }

    public Game create(Game game) {
        try {
            game = gameRepository.save(game);
            return game;
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Game name already in use. ");
        }
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
            throw new DatabaseIntegrityException("Name already in use. ");
        }
    }

    private void updateData(Game game, Game newGame) {
        game.setName       ((Objects.nonNull(newGame.getName()))        ? newGame.getName()        : game.getName());
        game.setDescription((Objects.nonNull(newGame.getDescription())) ? newGame.getDescription() : game.getDescription());
        game.setAgeGroup   ((Objects.nonNull(newGame.getAgeGroup()))    ? newGame.getAgeGroup()    : game.getAgeGroup());
        game.setPrice      ((Objects.nonNull(newGame.getPrice()))       ? newGame.getPrice()       : game.getPrice());
    }

}
