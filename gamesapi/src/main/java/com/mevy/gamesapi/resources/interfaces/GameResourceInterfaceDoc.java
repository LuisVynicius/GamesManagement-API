package com.mevy.gamesapi.resources.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mevy.gamesapi.entities.Game;
import com.mevy.gamesapi.entities.dtos.GameCreateDTO;
import com.mevy.gamesapi.entities.dtos.GameUpdateDTO;
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Game controller", description = "Controller for management games. ")
public interface GameResourceInterfaceDoc {
    
    @Operation(summary = "Return all games. ")
    ResponseEntity<List<Game>> findAll();

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
    ResponseEntity<Game> findById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            Long id
    );

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
    ResponseEntity<Game> findByName(
            @Parameter(
                description = "Provide an Name (String value)",
                required = true
            )
            String name
    );

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
    ResponseEntity<Void> create(
            @RequestBody(
                description = "A GameCreateDTO object must be used in the request body. ",
                content = @Content(schema = @Schema(implementation = GameCreateDTO.class)),
                required = true
            )
            GameCreateDTO gameCreateDTO
    );

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
    ResponseEntity<Void> delete(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            Long id
    );

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
    ResponseEntity<Void> update(
            @RequestBody(
                description = "A GameUpdateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = GameUpdateDTO.class)),
                required = true
            )
            GameUpdateDTO gameUpdateDTO
    );

}
