package com.mevy.gamesapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.repositories.UserRepository;

public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Load a user by username when everything is ok. ")
    void testLoadUserByUsernameCase01() {
        String name = "User01";
        when(userRepository.findByUsername(name)).thenReturn(new User());

        userDetailsServiceImpl.loadUserByUsername(name);

        verify(userRepository, times(1)).findByUsername(name);
    }

    @Test
    @DisplayName("Load a user by username when the user is not found. ")
    void testLoadUserByUsernameCase02() {
        String username = "User01";
        when(userRepository.findByUsername(username)).thenReturn(null);

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername(username);
        });

        verify(userRepository, times(1)).findByUsername(username);

        assertEquals("Username not found. Username: " + username, usernameNotFoundException.getMessage());
    }
}
