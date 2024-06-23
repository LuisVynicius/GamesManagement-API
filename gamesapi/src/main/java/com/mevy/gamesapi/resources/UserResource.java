package com.mevy.gamesapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.entities.dtos.UserCreateDTO;
import com.mevy.gamesapi.entities.dtos.UserUpdateDTO;
import com.mevy.gamesapi.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserResource {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);

    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        User user = userService.create(userCreateDTO.toUser());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{îd}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        User user = userUpdateDTO.toUser();
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

}
