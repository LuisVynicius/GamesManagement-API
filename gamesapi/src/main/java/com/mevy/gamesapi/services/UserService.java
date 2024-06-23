package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.repositories.UserRepository;
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
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound(User.class, id));
        return user;
    }

    public User create(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            return user;
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Email or Username already in use. ");
        }
    }

    public void delete(Long id) {
        findById(id);
        try{
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Database integrity violation error. ");
        }
    }

    public void update(User newUser) {
        try {
            User user = userRepository.getReferenceById(newUser.getId());
            updateData(user, newUser);
            userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(User.class, newUser.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseIntegrityException("Username already in use. ");
        }
    }

    private void updateData(User user, User newUser) {
        user.setUsername((Objects.nonNull(newUser.getUsername())) ? newUser.getUsername() : user.getUsername());
        user.setPassword((Objects.nonNull(newUser.getPassword())) ? newUser.getPassword() : user.getPassword());
    }

}
