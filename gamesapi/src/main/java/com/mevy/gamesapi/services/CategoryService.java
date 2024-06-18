package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.Category;
import com.mevy.gamesapi.repositories.CategoryRepository;

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
        category = categoryRepository.save(category);
        return category;
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public void update(Category newCategory) {
        Category category = categoryRepository.getReferenceById(newCategory.getId());
        updateData(category, newCategory);
        categoryRepository.save(category);
    }

    private void updateData(Category category, Category newCategory) {
        category.setName((Objects.nonNull(newCategory.getName())) ? newCategory.getName() : category.getName());
    }

}
