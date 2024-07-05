package com.mevy.gamesapi.resources.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.entities.dtos.UserCreateDTO;
import com.mevy.gamesapi.entities.dtos.UserUpdateDTO;
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Controller", description = "Controller for management user. ")
public interface UserResourceInterfaceDoc {
    
    @Operation(summary = "Return all users. ")
    ResponseEntity<List<User>> findAll();

    @Operation(
        summary = "Find a user by id.",
        description = "Retrieve information about a user by its id. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "User found successfully",
                content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    ResponseEntity<User> findById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            Long id
    );

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
    ResponseEntity<User> getCurrentUser();

    @Operation(
        summary = "Create a User. ",
        description = "This endpoint allows the creation of a new User by providing a UserCreateDTO object in the request body. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "User created successfully. "
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Email or Username already in use. ",
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
                description = "A UserCreateDTO object must be used in the request body. ",
                content = @Content(schema = @Schema(implementation = UserCreateDTO.class)),
                required = true
            )
            UserCreateDTO userCreateDTO
    );

    @Operation(
        summary = "Delete a user. ",
        description = "This endpoint allows the deletion of a user by providing its id "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "User deleted successfully. "
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Data integrity violation. ",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    ResponseEntity<Void> deleteById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            Long id
    );

    @Operation(
        summary = "Insert DeleteDate for the current user.",
        description = "This endpoint allows inserting a DeleteDate for the current authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "DeleteDate inserted successfully."
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    ResponseEntity<Void> addDeleteDateToCurrentUser();

    @Operation(
        summary = "Update current user information.",
        description = "This endpoint allows updating the information of the current authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "User information updated successfully."
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    ResponseEntity<Void> updateCurrentUser(
            @RequestBody(
                description = "A UserUpdateDTO object must be used in the request body. ",
                content = @Content(schema = @Schema(implementation = UserUpdateDTO.class)),
                required = true
            )
            UserUpdateDTO userUpdateDTO
    );

}
