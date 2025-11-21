package com.m01project.taskmanager.controller;


import com.m01project.taskmanager.dto.UserResponseDto;
import com.m01project.taskmanager.model.User;
import com.m01project.taskmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /api/users/{id} -> return a single user by id.
     *  - 200 OK with UserResponseDto if found
     *  - 404 Not Found if not present
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) {
        Optional<User> u = userService.getUserById(id);
        return u.map(user -> ResponseEntity.ok(new UserResponseDto(user.getId(), user.getEmail())))
                .orElseGet(() -> ResponseEntity.notFound().build());


    }
}
