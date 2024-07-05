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
import com.mevy.gamesapi.resources.interfaces.CategoryResourceInterfaceDoc;
import com.mevy.gamesapi.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryResource implements CategoryResourceInterfaceDoc {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok().body(categories);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> update(
            @RequestBody
            @Valid
            CategoryUpdateDTO categoryUpdateDTO
    ) {
        Category category = categoryService.fromDTO(categoryUpdateDTO);
        categoryService.update(category);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable 
            Long id
    ) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
