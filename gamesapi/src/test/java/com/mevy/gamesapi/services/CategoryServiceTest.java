package com.mevy.gamesapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.mevy.gamesapi.entities.Category;
import com.mevy.gamesapi.repositories.CategoryRepository;
import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

public class CategoryServiceTest {
    
    @Mock
    private CategoryRepository categoryRepository;

    @Autowired
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a category when everything is ok. ")
    void testCreateCase01() {
        Category category = new Category(null, "category01");
        when(categoryRepository.existsByName(category.getName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(new Category(1L, category.getName()));
        
        Category createdCategory = categoryService.create(category);

        verify(categoryRepository, times(1)).existsByName(category.getName());
        verify(categoryRepository, times(1)).save(category);
        
        assertNotNull(createdCategory);
        assertEquals(createdCategory.getName(), category.getName());
        assertEquals(1L, createdCategory.getId());
    }

    @Test
    @DisplayName("Create a category when the name is in use. ")
    void testCreateCase02() {
        Category category = new Category(null, "category01");
        when(categoryRepository.existsByName(category.getName())).thenReturn(true);

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            categoryService.create(category);
        });
        
        verify(categoryRepository, never()).save(any(Category.class));
        
        assertEquals("Category already exists. ", databaseIntegrityException.getMessage());
    }

    @Test
    @DisplayName("Delete a category when everything is ok. ")
    void testDeleteCase01() {
        Category category = new Category(1L, "Category01");
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).deleteById(category.getId());

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).findById(category.getId());
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete a category when the category is not found. ")
    void testDeleteCase02() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class, () -> {
            categoryService.delete(id);
        });

        verify(categoryRepository, times(1)).findById(id);

        assertEquals("Category Not Found. Identifier: " + id, resourceNotFound.getMessage());
    }

    @Test
    @DisplayName("Delete a category when database integrity violation. ")
    void testDeleteCase03() {
        Category category = new Category(1L, "Category01");
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        doThrow(new DataIntegrityViolationException("")).when(categoryRepository).deleteById(category.getId());

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            categoryService.delete(category.getId());
        });

        verify(categoryRepository, times(1)).deleteById(category.getId());
        verify(categoryRepository, times(1)).findById(category.getId());

        assertEquals("Database integrity violation error. ", databaseIntegrityException.getMessage());
    }

    @Test
    @DisplayName("Update a category when everything is ok. ")
    void testUpdateCase01() {
        Category category = new Category(1L, "Category01");
        Category updatedCategory = new Category(1L, "Category01Updated");
        when(categoryRepository.getReferenceById(category.getId())).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        categoryService.update(updatedCategory);

        verify(categoryRepository, times(1)).getReferenceById(category.getId());
        verify(categoryRepository, times(1)).save(category);
        
        assertEquals(updatedCategory.getName(), category.getName());
    }

    @Test
    @DisplayName("Update a category when the category is not found. ")
    void testUpdateCase02() {
        Category category = new Category(1L, "Category01");
        Category updatedCategory = new Category(1L, "Category01Updated");
        doThrow(new EntityNotFoundException("")).when(categoryRepository).getReferenceById(category.getId());

        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class, () -> {
            categoryService.update(updatedCategory);
        });

        verify(categoryRepository, times(1)).getReferenceById(category.getId());
        verify(categoryRepository, never()).save(category);

        assertEquals("Category Not Found. Identifier: " + category.getId(), resourceNotFound.getMessage());
    }

    @Test
    @DisplayName("Update a category when data base integrity violation. ")
    void testUpdateCase03() {
        Category category = new Category(1L, "Category01");
        Category updatedCategory = new Category(1L, "Category01Updated");
        when(categoryRepository.getReferenceById(category.getId())).thenReturn(category);
        doThrow(new DataIntegrityViolationException("")).when(categoryRepository).save(category);

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            categoryService.update(updatedCategory);
        });

        verify(categoryRepository, times(1)).getReferenceById(category.getId());
        verify(categoryRepository, times(1)).save(category);
        
        assertEquals("Name already in use. ", databaseIntegrityException.getMessage());

    }

}
