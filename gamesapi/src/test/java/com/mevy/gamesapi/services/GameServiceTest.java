package com.mevy.gamesapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.mevy.gamesapi.entities.Game;
import com.mevy.gamesapi.entities.dtos.GameCreateDTO;
import com.mevy.gamesapi.entities.dtos.GameUpdateDTO;
import com.mevy.gamesapi.repositories.GameRepository;
import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Autowired
    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a game when everything is ok. ")
    void testCreateCase01() {
        GameCreateDTO gameCreateDTO = new GameCreateDTO(
                "Game01",
                50.00f,
                "Description01",
                Instant.now(),
                (short) 18,
                false
            );
        Game game = gameService.fromDTO(gameCreateDTO);
        when(gameRepository.existsByName(game.getName())).thenReturn(false);
        when(gameRepository.save(game)).thenReturn(
            new Game(
                1L,
                game.getName(),
                game.getPrice(),
                game.getDescription(),
                game.getDate(),
                game.getAgeGroup(),
                game.getDisabled()
            )
        );

        Game createdgame = gameService.create(game);

        verify(gameRepository, times(1)).existsByName(game.getName());
        verify(gameRepository, times(1)).save(game);

        assertNotNull(createdgame);
        assertEquals(1L, createdgame.getId());
        assertEquals(game.getName(), createdgame.getName());
        assertEquals(game.getPrice(), createdgame.getPrice());
        assertEquals(game.getDescription(), createdgame.getDescription());
        assertEquals(game.getDate(), createdgame.getDate());
        assertEquals(game.getAgeGroup(), createdgame.getAgeGroup());
        assertEquals(game.getDisabled(), createdgame.getDisabled());
    }

    @Test
    @DisplayName("Create a game when game name is in use. ")
    void testCreateCase02() {
        GameCreateDTO gameCreateDTO = new GameCreateDTO(
                "Game01",
                50.00f,
                "Description01",
                Instant.now(),
                (short) 18,
                false
            );
        Game game = gameService.fromDTO(gameCreateDTO);
        when(gameRepository.existsByName(game.getName())).thenReturn(true);

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            gameService.create(game);
        });

        verify(gameRepository, times(1)).existsByName(game.getName());
        verify(gameRepository, never()).save(game);

        assertEquals("Game name already in use. ", databaseIntegrityException.getMessage());
    }

    @Test
    @DisplayName("Delete a game when everything is ok. ")
    void testDeleteCase01() {
        Long id = 1L;
        when(gameRepository.findById(id)).thenReturn(Optional.of(new Game()));
        doNothing().when(gameRepository).deleteById(id);

        gameService.delete(id);

        verify(gameRepository, times(1)).findById(id);
        verify(gameRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Delete a game when database integrity violation. ")
    void testDeleteCase02() {
        Long id = 1L;
        when(gameRepository.findById(id)).thenReturn(Optional.of(new Game()));
        doThrow(new DataIntegrityViolationException("")).when(gameRepository).deleteById(id);

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            gameService.delete(id);
        });

        verify(gameRepository, times(1)).findById(id);
        verify(gameRepository, times(1)).deleteById(id);

        assertEquals("Database integrity violation error. ", databaseIntegrityException.getMessage());            
    }

    @Test
    @DisplayName("Find a game when everything is ok. ")
    void testFindByIdCase01() {
        Long id = 1L;
        when(gameRepository.findById(id)).thenReturn(Optional.of(new Game()));

        Game game = gameService.findById(id);

        verify(gameRepository, times(1)).findById(id);
        
        assertNotNull(game);
    }

    @Test
    @DisplayName("Find a game when the game is not found. ")
    void testFindByIdCase02() {
        Long id = 1L;
        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class, () -> {
            gameService.findById(id);
        });

        verify(gameRepository, times(1)).findById(id);

        assertEquals("Game Not Found. Identifier: " + id, resourceNotFound.getMessage());
    }

    @Test
    @DisplayName("Find a game(with the name) when everything is ok. ")
    void testFindByNameCase01() {
        String name = "Game01";
        when(gameRepository.findByName(name)).thenReturn(Optional.of(new Game()));

        Game game = gameService.findByName(name);

        verify(gameRepository, times(1)).findByName(name);

        assertNotNull(game);    
    }

    @Test
    @DisplayName("Find a game(with the name) when the game is not found. ")
    void testFindByNameCase02() {
        String name = "Game01";
        when(gameRepository.findByName(name)).thenReturn(Optional.empty());

        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class, () -> {
            gameService.findByName(name);
        });

        verify(gameRepository, times(1)).findByName(name);

        assertEquals("Game Not Found. Identifier: " + name, resourceNotFound.getMessage());
    }

    @Test
    @DisplayName("Update a game when everything is ok. ")
    void testUpdateCase01() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(
                1L,
                "Game01Updated",
                50.00f,
                "Description01Updated",
                (short) 18,
                false
            );
        Game updatedgame = gameService.fromDTO(gameUpdateDTO);
        Game game = new Game(
            1L,
            "Game01",
            15.00f,
            "Description01",
            Instant.now(),
            (short) 18,
            false
        );
        when(gameRepository.getReferenceById(updatedgame.getId())).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);

        gameService.update(updatedgame);

        verify(gameRepository, times(1)).getReferenceById(updatedgame.getId());
        verify(gameRepository, times(1)).save(game);

        assertEquals(1L, updatedgame.getId());
        assertEquals(game.getName(), updatedgame.getName());
        assertEquals(game.getPrice(), updatedgame.getPrice());
        assertEquals(game.getDescription(), updatedgame.getDescription());
        assertEquals(game.getAgeGroup(), updatedgame.getAgeGroup());
        assertEquals(game.getDisabled(), updatedgame.getDisabled());
    }

    @Test
    @DisplayName("Update a game when game name is in use. ")
    void testUpdateCase02() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(
                1L,
                "Game01Updated",
                50.00f,
                "Description01Updated",
                (short) 18,
                false
            );
        Game updatedgame = gameService.fromDTO(gameUpdateDTO);
        doThrow(new EntityNotFoundException("")).when(gameRepository).getReferenceById(updatedgame.getId());

        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class, () -> {
            gameService.update(updatedgame);
        });

        verify(gameRepository, times(1)).getReferenceById(updatedgame.getId());
        verify(gameRepository, never()).save(any(Game.class));

        assertEquals("Game Not Found. Identifier: " + updatedgame.getId(), resourceNotFound.getMessage());        
    }

    @Test
    @DisplayName("Update a game when database integrity violation. ")
    void testUpdateCase03() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(
                1L,
                "Game01Updated",
                50.00f,
                "Description01Updated",
                (short) 18,
                false
            );
        Game updatedgame = gameService.fromDTO(gameUpdateDTO);
        Game game = new Game(
            1L,
            "Game01",
            15.00f,
            "Description01",
            Instant.now(),
            (short) 18,
            false
        );
        when(gameRepository.getReferenceById(updatedgame.getId())).thenReturn(game);
        doThrow(new DataIntegrityViolationException("")).when(gameRepository).save(game);

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            gameService.update(updatedgame);
        });

        verify(gameRepository, times(1)).getReferenceById(updatedgame.getId());
        verify(gameRepository, times(1)).save(game);

        assertEquals("Game name already in use. ", databaseIntegrityException.getMessage());        
    }
}
