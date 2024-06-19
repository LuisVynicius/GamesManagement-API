package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.Category;
import com.mevy.gamesapi.repositories.CategoryRepository;
import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category create(Category category) {
        try {
            category = categoryRepository.save(category);
            return category;
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Category already exists. ");
        }
    }

    public void delete(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound(Category.class, id));
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Database integrity violation error. ");
        }
    }

    public void update(Category newCategory) {
        try {
            Category category = categoryRepository.getReferenceById(newCategory.getId());
            updateData(category, newCategory);
            categoryRepository.save(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(Category.class, newCategory.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Name already in use. ");
        }
    }

    private void updateData(Category category, Category newCategory) {
        category.setName((Objects.nonNull(newCategory.getName())) ? newCategory.getName() : category.getName());
    }

}
