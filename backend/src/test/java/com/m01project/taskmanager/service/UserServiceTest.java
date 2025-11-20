package com.m01project.taskmanager.service;


import com.m01project.taskmanager.model.User;
import com.m01project.taskmanager.repository.UserRepository;
import com.m01project.taskmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(UserServiceImpl.class) // brings the service into the JPA slice
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})

public class UserServiceTest {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    UserServiceTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("create(): should save user and assign ID")
    void create_shouldPersistUser() {
        User saved = userService.createUser("d@k.com", "secret");

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("d@k.com");

        // verify exists via repository
        Optional<User> fromDb = userRepository.findByEmail("d@k.com");
        assertThat(fromDb).isPresent();
        assertThat(fromDb.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("create(): should fail on duplicate email")
    void create_duplicateEmail_shouldFail() {
        userService.createUser("dup@mail.com", "x");
        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser("dup@mail.com", "y"));
    }

    @Test
    @DisplayName("getByEmail(): should return saved user")
    void getByEmail_shouldReturnUser() {
        User saved = userService.createUser("find@me.com", "p");
        Optional<User> found = userService.getUserByEmail("find@me.com");

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
        assertThat(found.get().getEmail()).isEqualTo("find@me.com");
    }
}