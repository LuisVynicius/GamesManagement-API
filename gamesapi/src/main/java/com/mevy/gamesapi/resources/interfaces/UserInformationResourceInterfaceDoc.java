package com.mevy.gamesapi.resources.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.entities.dtos.UserInformationsUpdateDTO;
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User informations Controller", description = "Controller for management user informations. ")
public interface UserInformationResourceInterfaceDoc {

    @Operation(summary = "Return all UserInformations. ")
    ResponseEntity<List<UserInformations>> findAll();

    @Operation(
        summary = "Return information about the current user.",
        description = "Retrieve detailed information about the currently authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "UserInformations found successfully",
                content = @Content(schema = @Schema(implementation = UserInformations.class))
            )
        }
    )
    ResponseEntity<UserInformations> getCurrentUserInformations();

    @Operation(
        summary = "Find a UserInformations by id.",
        description = "Retrieve information about a UserInformations by its id. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Game found successfully",
                content = @Content(schema = @Schema(implementation = UserInformations.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "UserInformations not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    ResponseEntity<UserInformations> findById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            Long id
    );

    @Operation(
        summary = "Update User Information.",
        description = "This endpoint allows updating the logged-in user's information by providing a UserInformationsUpdateDTO object in the request body."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "UserInformations updated successfully. "
            ),
            @ApiResponse(
                responseCode = "404",
                description = "UserInformations not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "422",
                description = "Validation error. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    ResponseEntity<Void> updateCurrentUserInformations(
            @RequestBody(
                description = "A UserInformationsUpdateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = UserInformationsUpdateDTO.class)),
                required = true
            )
            UserInformationsUpdateDTO userInformationsUpdateDTO
    );
    
}
