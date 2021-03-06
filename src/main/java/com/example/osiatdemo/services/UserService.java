package com.example.osiatdemo.services;


import com.example.osiatdemo.error.InvalidRequestParamTypeException;
import com.example.osiatdemo.error.UserNotFoundException;
import com.example.osiatdemo.model.User;
import com.example.osiatdemo.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public User getUserById(String id) {

        int num;
        try {
            num = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            throw new InvalidRequestParamTypeException(id, ex.getMessage());
        }
        return userRepo.findById(num)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    public User[] getAllUsers() {
        List<User> users = StreamSupport
                .stream(userRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return users.stream().toArray(User[]::new);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public boolean deleteUserById(String userId) {

        int num;
        try {
            num = Integer.parseInt(userId);
        } catch (NumberFormatException ex) {
            throw new InvalidRequestParamTypeException(userId, ex.getMessage());
        }
        if (userRepo.findById(num).orElse(null) == null) {
            return false;
        } else {
            userRepo.deleteById(num);
            return true;
        }
    }
}
