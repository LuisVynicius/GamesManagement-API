package com.mevy.gamesapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.repositories.UserRepository;
import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a user when everything is ok. ")
    void testCreateCase01() {
        User user = new User(
                null,
                "User01",
                "Password01",
                "Email01"
            );
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(user.getPassword() + "crypto");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        verify(userRepository, times(1)).existsByUsername(user.getUsername());
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);

        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals("Password01crypto", createdUser.getPassword());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    @DisplayName("Create a user when username or email is in use. ")
    void testCreateCase02() {
        User user = new User(
                null,
                "User01",
                "Password01",
                "Email01"
            );
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        when(userRepository.existsByEmail   (user.getEmail   ())).thenReturn(true);

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            userService.create(user);
        });

        verify(bCryptPasswordEncoder, never()).encode(user.getPassword());
        verify(userRepository, never()).save(user);

        assertEquals("Email or Username already in use. ", databaseIntegrityException.getMessage());
    }

    @Test
    @DisplayName("Delete a user when everything is ok. ")
    void testDeleteByIdCase01() {
        User user = new User(1L, "User01", "Password01", "Email01");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        userService.deleteById(user.getId());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    @DisplayName("Delete a user when database integrity violation. ")
    void testDeleteByIdCase02() {
        User user = new User(1L, "User01", "Password01", "Email01");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doThrow(new DataIntegrityViolationException("")).when(userRepository).deleteById(user.getId());

        DatabaseIntegrityException databaseIntegrityException = assertThrows(DatabaseIntegrityException.class, () -> {
            userService.deleteById(user.getId());
        });

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());

        assertEquals("Database integrity violation error. ", databaseIntegrityException.getMessage());
    }

    @Test
    @DisplayName("Find a user when everything is ok. ")
    void testFindByIdCase01() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        User user = userService.findById(id);

        verify(userRepository, times(1)).findById(id);
        
        assertNotNull(user);
    }

    @Test
    @DisplayName("Find a user when the user is not found. ")
    void testFindByIdCase02() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class, () -> {
            userService.findById(id);
        });

        verify(userRepository, times(1)).findById(id);
        
        assertEquals("User Not Found. Identifier: " + id, resourceNotFound.getMessage());
    }
}
