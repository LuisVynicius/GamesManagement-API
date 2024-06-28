package com.mevy.gamesapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.repositories.UserInformationsRepository;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

public class UserInformationsServiceTest {

    @Mock
    private UserInformationsRepository userInformationsRepository;

    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private UserInformationsService userInformationsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Find a UserInformations when everything is ok. ")
    void testFindByIdCase01() {
        Long id = 1L;
        when(userInformationsRepository.findById(id)).thenReturn(Optional.of(new UserInformations()));

        UserInformations userInformations = userInformationsService.findById(id);

        verify(userInformationsRepository, times(1)).findById(id);
        
        assertNotNull(userInformations);
    }

    @Test
    @DisplayName("Find a UserInformations when the UserInformations is not found. ")
    void testFindByIdCase02() {
        Long id = 1L;
        when(userInformationsRepository.findById(id)).thenReturn(Optional.empty());
        

        ResourceNotFound resourceNotFound = assertThrows(ResourceNotFound.class, () -> {
            userInformationsService.findById(id);
        });

        verify(userInformationsRepository, times(1)).findById(id);
        
        assertEquals("UserInformations Not Found. Identifier: " + id, resourceNotFound.getMessage());
    }
    
}
