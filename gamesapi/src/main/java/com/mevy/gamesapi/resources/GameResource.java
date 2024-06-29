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
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;
import com.mevy.gamesapi.services.GameService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/game")
@Tag(name = "Game controller", description = "Controller for management games. ")
public class GameResource {
    
    @Autowired
    private GameService gameService;

    @Operation(summary = "Return all games. ")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Game>> findAll() {
        List<Game> games = gameService.findAll();
        return ResponseEntity.ok().body(games);
    }

    @Operation(
        summary = "Find a game by id.",
        description = "Retrieve information about a game by its id. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Game found successfully",
                content = @Content(schema = @Schema(implementation = Game.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Game not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            @PathVariable
            Long id
    ) {
        Game game = gameService.findById(id);
        return ResponseEntity.ok().body(game);
    }

    @Operation(
        summary = "Find a game by name.",
        description = "Retrieve information about a game by its name. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Game found successfully",
                content = @Content(schema = @Schema(implementation = Game.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Game not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/name/{name}")
    public ResponseEntity<Game> findByName(
            @Parameter(
                description = "Provide an Name (String value)",
                required = true
            )
            @PathVariable
            String name
    ) {
        Game game = gameService.findByName(name);
        return ResponseEntity.ok().body(game);
    }

    @Operation(
        summary = "Create a game. ",
        description = "This endpoint allows the creation of a new game by providing a GameCreateDTO object in the request body. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Game created successfully. "
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Name already in use. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "422",
                description = "Validation error. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GAME_DEVELOPER')")
    @PostMapping
    public ResponseEntity<Void> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "A GameCreateDTO object must be used in the request body. ",
                content = @Content(schema = @Schema(implementation = GameCreateDTO.class)),
                required = true
            )
            @RequestBody
            @Valid
            GameCreateDTO gameCreateDTO
    ) {
        Game game = gameService.fromDTO(gameCreateDTO);
        game = gameService.create(game);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{îd}")
                        .buildAndExpand(game.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(
        summary = "Delete a game. ",
        description = "This endpoint allows the deletion of a game by providing its id "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "Game deleted successfully. "
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Game not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Data integrity violation. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            @PathVariable
            Long id
    ) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Update a Game. ",
        description = "This endpoint allows updating an existing game by providing a GameUpdateDTO object in the request body."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "Game updated successfully. "
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Game not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Name already in use. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "422",
                description = "Validation error. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "A GameUpdateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = GameUpdateDTO.class)),
                required = true
            )
            @RequestBody
            @Valid
            GameUpdateDTO gameUpdateDTO
    ) {
        Game game = gameService.fromDTO(gameUpdateDTO);
        gameService.update(game);
        return ResponseEntity.noContent().build();
    }

}
