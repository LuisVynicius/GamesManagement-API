package com.mevy.gamesapi.configs;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.mevy.gamesapi.entities.Category;
import com.mevy.gamesapi.entities.Game;
import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.repositories.CategoryRepository;
import com.mevy.gamesapi.repositories.GameRepository;
import com.mevy.gamesapi.repositories.UserRepository;

@Configuration
public class Seed implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.save(new User(null, "User01", "Password01", "Email01"));
        Game game = gameRepository.save(new Game(null, "Game01", 150.00f, "Description01", Instant.now(), (short)16, true));
        Category category = categoryRepository.save(new Category(null, "Category01"));
        
        user.getGames().add(game);
        userRepository.save(user);

        game.getCategories().add(category);
        gameRepository.save(game);
    }
    
}
