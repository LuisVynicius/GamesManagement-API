package com.mevy.gamesapi.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.gamesapi.entities.User;
import com.mevy.gamesapi.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    public User create(User user) {
        user = userRepository.save(user);
        return user;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void update(User newUser) {
        User user = userRepository.getReferenceById(newUser.getId());
        updateData(user, newUser);
        userRepository.save(user);
    }

    private void updateData(User user, User newUser) {
        user.setUsername((Objects.nonNull(newUser.getUsername())) ? newUser.getUsername() : user.getUsername());
        user.setPassword((Objects.nonNull(newUser.getPassword())) ? newUser.getPassword() : user.getPassword());
    }

}
