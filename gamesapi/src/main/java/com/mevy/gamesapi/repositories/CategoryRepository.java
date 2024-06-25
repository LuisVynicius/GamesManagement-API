package com.mevy.gamesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    @Transactional(readOnly = true)
    Boolean existsByName(String name);

}