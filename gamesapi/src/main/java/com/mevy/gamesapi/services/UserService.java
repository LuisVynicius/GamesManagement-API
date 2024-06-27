package com.mevy.gamesapi.services;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.entities.dtos.UserCreateDTO;
import com.mevy.gamesapi.entities.dtos.UserUpdateDTO;
import com.mevy.gamesapi.entities.enums.ProfileEnum;
import com.mevy.gamesapi.repositories.UserRepository;
import com.mevy.gamesapi.security.UserSpringSecurity;
import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFound(User.class, id)
        );
        return user;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        User user = userRepository.findById(authenticated().getId()).get();
        return user;
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new DatabaseIntegrityException("Email or Username already in use. ");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        
        UserInformations userInformations = new UserInformations(user);
        userInformations.setCreateAt(Instant.now());
        
        user.setUserInformations(userInformations);
        user = userRepository.save(user);

        return user;
    }

    public void deleteById(Long id) {
        findById(id);
        try{
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Database integrity violation error. ");
        }
    }

    public void addDeleteDateToCurrentUser() {
        Long id = authenticated().getId();
        try {
            User user = userRepository.getReferenceById(id);
            user.setDeleteDate(Instant.now());
            userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(User.class, id);
        }
    }

    public void updateCurrentUser(User newUser) {
        Long id = authenticated().getId();
        try {
            User user = userRepository.getReferenceById(id);
            updateData(user, newUser);
            userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(User.class, id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Username already in use. ");
        }
    }

    private void updateData(User user, User newUser) {
        user.setUsername((Objects.nonNull(newUser.getUsername())) ? newUser.getUsername() : user.getUsername());
        user.setPassword((Objects.nonNull(newUser.getPassword())) ? bCryptPasswordEncoder.encode(newUser.getPassword()) : user.getPassword());
    }

    public User fromDTO(UserCreateDTO userCreateDTO) {
        User user = new User(
                    null,
                    userCreateDTO.username(),
                    userCreateDTO.password(),
                    userCreateDTO.email()
                );
        return user;
    }

    public User fromDTO(UserUpdateDTO userUpdateDTO) {
        User user = new User(
                   userUpdateDTO.id(),
                   userUpdateDTO.username(),
                   userUpdateDTO.password(),
                   null
                );
        return user;
    }

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
