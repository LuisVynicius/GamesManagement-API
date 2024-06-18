package com.mevy.gamesapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.mevy.gamesapi.entities.dto.GameCreateDTO;
import com.mevy.gamesapi.entities.dto.GameUpdateDTO;
import com.mevy.gamesapi.services.GameService;

@RestController
@RequestMapping("/game")
public class GameResource {
    
    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> findAll() {
        List<Game> games = gameService.findAll();
        return ResponseEntity.ok().body(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(@PathVariable Long id) {
        Game game = gameService.findById(id);
        return ResponseEntity.ok().body(game);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody GameCreateDTO gameCreateDTO) {
        Game game = gameService.create(gameCreateDTO.toGame());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{îd}").buildAndExpand(game.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody GameUpdateDTO gameUpdateDTO) {
        Game game = gameUpdateDTO.toGame();
        gameService.update(game);
        return ResponseEntity.noContent().build();
    }

}
