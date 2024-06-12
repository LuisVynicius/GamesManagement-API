package com.mevy.gamesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.gamesapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
