package com.mevy.gamesapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.entities.dtos.UserInformationsUpdateDTO;
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;
import com.mevy.gamesapi.services.UserInformationsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/userInformations")
@Tag(name = "User informations Controller", description = "Controller for management user informations. ")
public class UserInformationsResource {
    
    @Autowired
    private UserInformationsService userInformationsService;

    @Operation(summary = "Return all UserInformations. ")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserInformations>> findAll() {
        List<UserInformations> userinformations = userInformationsService.findAll();
        return ResponseEntity.ok().body(userinformations);
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
    public ResponseEntity<UserInformations> getCurrentUserInformations() {
        UserInformations userInformations = userInformationsService.getCurrentUserInformations();
        return ResponseEntity.ok().body(userInformations);
    }

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserInformations> findById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            @PathVariable
            Long id
    ) {
        UserInformations userInformations = userInformationsService.findById(id);
        return ResponseEntity.ok().body(userInformations);
    }

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
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/current")
    public ResponseEntity<Void> updateCurrentUserInformations(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "A UserInformationsUpdateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = UserInformationsUpdateDTO.class)),
                required = true
            )
            @RequestBody
            UserInformationsUpdateDTO userInformationsUpdateDTO
    ) {
        UserInformations userInformations = userInformationsService.fromDTO(userInformationsUpdateDTO);
        userInformationsService.updateCurrentUserInformations(userInformations);
        return ResponseEntity.noContent().build();
    }

}
