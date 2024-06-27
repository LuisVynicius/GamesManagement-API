package com.mevy.gamesapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mevy.gamesapi.entities.UserInformations;

public interface UserInformationsRepository extends JpaRepository<UserInformations, Long>{
    
}
