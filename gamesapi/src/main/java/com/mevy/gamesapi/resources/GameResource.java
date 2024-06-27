package com.mevy.gamesapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mevy.gamesapi.entities.Game;
import com.mevy.gamesapi.entities.dtos.GameCreateDTO;
import com.mevy.gamesapi.entities.dtos.GameUpdateDTO;
import com.mevy.gamesapi.services.GameService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/game")
public class GameResource {
    
    @Autowired
    private GameService gameService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Game>> findAll() {
        List<Game> games = gameService.findAll();
        return ResponseEntity.ok().body(games);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id) {
        Game game = gameService.findById(id);
        return ResponseEntity.ok().body(game);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/name/{name}")
    public ResponseEntity<Game> findByName(@PathVariable String name) {
        Game game = gameService.findByName(name);
        return ResponseEntity.ok().body(game);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GAME_DEVELOPER')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid GameCreateDTO gameCreateDTO) {
        Game game = gameService.fromDTO(gameCreateDTO);
        game = gameService.create(game);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{îd}")
                        .buildAndExpand(game.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid GameUpdateDTO gameUpdateDTO) {
        Game game = gameService.fromDTO(gameUpdateDTO);
        gameService.update(game);
        return ResponseEntity.noContent().build();
    }

}
