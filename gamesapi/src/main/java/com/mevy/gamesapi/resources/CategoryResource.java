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

import com.mevy.gamesapi.entities.Category;
import com.mevy.gamesapi.entities.dtos.CategoryCreateDTO;
import com.mevy.gamesapi.entities.dtos.CategoryUpdateDTO;
import com.mevy.gamesapi.resources.exceptions.ErrorResponse;
import com.mevy.gamesapi.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
@Tag(name = "Category Controller", description = "Controller for management categories. ")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Return all categories. ")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok().body(categories);
    }

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "A CategoryCreateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = CategoryCreateDTO.class)),
                required = true
            )
            @RequestBody
            @Valid
            CategoryCreateDTO categoryCreateDTO
    ) {
        Category category = categoryService.fromDTO(categoryCreateDTO);
        category = categoryService.create(category);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(category.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "A CategoryUpdateDTO object must be used in the request body.",
                content = @Content(schema = @Schema(implementation = CategoryUpdateDTO.class)),
                required = true
            )
            @RequestBody
            @Valid CategoryUpdateDTO categoryUpdateDTO
    ) {
        Category category = categoryService.fromDTO(categoryUpdateDTO);
        categoryService.update(category);
        return ResponseEntity.noContent().build();
    }

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
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
