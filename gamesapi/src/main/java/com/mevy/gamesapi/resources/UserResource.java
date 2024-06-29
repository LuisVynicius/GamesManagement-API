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

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.entities.dtos.UserCreateDTO;
import com.mevy.gamesapi.entities.dtos.UserUpdateDTO;
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;
import com.mevy.gamesapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Controller for management user. ")
public class UserResource {
    
    @Autowired
    private UserService userService;

    @Operation(summary = "Return all users. ")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            @PathVariable
            Long id
    ) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok().body(user);
    }

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
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<Void> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "A UserCreateDTO object must be used in the request body. ",
                content = @Content(schema = @Schema(implementation = UserCreateDTO.class)),
                required = true
            )
            @RequestBody
            @Valid
            UserCreateDTO userCreateDTO
    ) {
        User user = userService.fromDTO(userCreateDTO);
        user = userService.create(user);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{îd}")
                        .buildAndExpand(user.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            @PathVariable
            Long id
    ) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

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
    })
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/current")
    public ResponseEntity<Void> addDeleteDateToCurrentUser() {
        userService.addDeleteDateToCurrentUser();
        return ResponseEntity.noContent().build();
    }

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
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/current")
    public ResponseEntity<Void> updateCurrentUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "A UserUpdateDTO object must be used in the request body. ",
                content = @Content(schema = @Schema(implementation = UserUpdateDTO.class)),
                required = true
            )
            @RequestBody
            @Valid
            UserUpdateDTO userUpdateDTO
    ) {
        User user = userService.fromDTO(userUpdateDTO);
        userService.updateCurrentUser(user);
        return ResponseEntity.noContent().build();
    }

}