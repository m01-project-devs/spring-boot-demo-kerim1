package com.m01project.taskmanager.service;

import com.m01project.taskmanager.model.User;
import java.util.Optional;
import java.util.List;

public interface UserService {
    User createUser(String email, String password);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    User updatePassword(String newPassword, Long id);
    void deleteUser(Long id);
    List<User> getAllUsers();
}
