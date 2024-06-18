package com.mevy.gamesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.gamesapi.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
