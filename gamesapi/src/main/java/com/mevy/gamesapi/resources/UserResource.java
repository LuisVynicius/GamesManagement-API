package com.mevy.gamesapi.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mevy.gamesapi.services.UserService;

@RestController
@RequestMapping("/game")
public class UserResource {
    
    @Autowired
    private UserService userService;

}
