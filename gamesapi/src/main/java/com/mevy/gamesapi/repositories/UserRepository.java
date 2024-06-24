package com.mevy.gamesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    @Transactional(readOnly = true)
    User findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
}
