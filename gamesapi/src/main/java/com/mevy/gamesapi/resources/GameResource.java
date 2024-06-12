package com.mevy.gamesapi.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mevy.gamesapi.services.GameService;

@RestController
@RequestMapping("/game")
public class GameResource {
    
    @Autowired
    private GameService gameService;

}
