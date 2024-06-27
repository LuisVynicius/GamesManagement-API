package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.entities.dtos.UserInformationsUpdateDTO;
import com.mevy.gamesapi.repositories.UserInformationsRepository;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserInformationsService {
    
    @Autowired
    private UserInformationsRepository userInformationsRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public List<UserInformations> findAll() {
        List<UserInformations> userinformations = userInformationsRepository.findAll();
        return userinformations;
    }

    @Transactional(readOnly = true)
    public UserInformations findById(Long id) {
        UserInformations userinformations = userInformationsRepository.findById(id).orElseThrow(
            () -> new ResourceNotFound(UserInformations.class, id)
        );
        return userinformations;
    }

    @Transactional(readOnly = true)
    public UserInformations getCurrentUserInformations() {
        UserInformations user = userInformationsRepository.findById(UserService.authenticated().getId()).get();
        return user;
    }

    public void updateCurrentUserInformations(UserInformations newUserInformations) {
        Long id = userService.getCurrentUser().getId();
        try {
            UserInformations userInformations = userInformationsRepository.getReferenceById(id);
            updateData(userInformations, newUserInformations);
            userInformationsRepository.save(userInformations);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(UserInformations.class, id);
        }
    }

    private void updateData(UserInformations userInformations, UserInformations newUserInformations) {
        userInformations.setName    ((Objects.nonNull(newUserInformations.getName())     ? newUserInformations.getName()     : userInformations.getName()));
        userInformations.setLastName((Objects.nonNull(newUserInformations.getLastName()) ? newUserInformations.getLastName() : userInformations.getLastName()));
    }

    public UserInformations fromDTO(UserInformationsUpdateDTO userInformationsUpdateDTO) {
        UserInformations userInformations = new UserInformations();
        userInformations.setName(userInformationsUpdateDTO.name());
        userInformations.setLastName(userInformationsUpdateDTO.lastName());
        return userInformations;
    }

}
