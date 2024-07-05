package com.mevy.gamesapi.resources.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mevy.gamesapi.entities.Category;
import com.mevy.gamesapi.entities.dtos.CategoryCreateDTO;
import com.mevy.gamesapi.entities.dtos.CategoryUpdateDTO;
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category Controller", description = "Controller for management categories. ")
public interface CategoryResourceInterfaceDoc {
    
    @Operation(summary = "Return all categories. ")
    ResponseEntity<List<Category>> findAll();

    @Operation(
        summary = "Create a category. ",
        description = "This endpoint allows the creation of a new category by providing a CategoryCreateDTO object in the request body. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201",
                description = "Category created successfully. "
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Category already exists. ",
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
                description = "A CategoryCreateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = CategoryCreateDTO.class)),
                required = true
            )
            CategoryCreateDTO categoryCreateDTO
    );

    @Operation(
        summary = "Create a category. ",
        description = "This endpoint allows updating an existing category by providing a CategoryUpdateDTO object in the request body."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "Category updated successfully. "
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Category not found. ",
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
                description = "A CategoryUpdateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = CategoryUpdateDTO.class)),
                required = true
            )
            CategoryUpdateDTO categoryUpdateDTO
    );

    @Operation(
        summary = "Delete a category. ",
        description = "This endpoint allows the deletion of a category by providing its id "
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "204",
                description = "Category deleted successfully. "
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Category not found. ",
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

}
