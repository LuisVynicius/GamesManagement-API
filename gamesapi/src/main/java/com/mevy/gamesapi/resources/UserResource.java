package com.mevy.gamesapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.mevy.gamesapi.resources.interfaces.UserResourceInterfaceDoc;
import com.mevy.gamesapi.services.UserService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserResource implements UserResourceInterfaceDoc {
    
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(
            @PathVariable
            Long id
    ) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody
            @Valid
            UserCreateDTO userCreateDTO
    ) {
        User user = userService.fromDTO(userCreateDTO);
        user = userService.create(user);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{îd}")
                        .buildAndExpand(user.getId())
                        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(
                description = "Provide an ID (Long value)",
                required = true
            )
            @PathVariable
            Long id
    ) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/current")
    public ResponseEntity<Void> addDeleteDateToCurrentUser() {
        userService.addDeleteDateToCurrentUser();
        return ResponseEntity.noContent().build();
    }

    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/current")
    public ResponseEntity<Void> updateCurrentUser(
            @RequestBody
            @Valid
            UserUpdateDTO userUpdateDTO
    ) {
        User user = userService.fromDTO(userUpdateDTO);
        userService.updateCurrentUser(user);
        return ResponseEntity.noContent().build();
    }

}