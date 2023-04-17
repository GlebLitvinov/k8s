package com.epam.users.controller;

import com.epam.users.dto.CreateUserRequest;
import com.epam.users.dto.UserDto;
import com.epam.users.entity.UserEntity;
import com.epam.users.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    private final UsersRepository repository;

    @GetMapping
    public String greetings() {
        return "Hello, k8s!";
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return repository.findById(id).map(this::buildUserDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/users/")
    public UserDto createUser(@RequestBody CreateUserRequest request) {
        var entity = repository.save(new UserEntity(null, request.getUsername(), 0));
        return buildUserDto(entity);
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody CreateUserRequest request) {
        var entity = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        entity.setUsername(request.getUsername());
        entity.setAmountOfPosts(request.getAmountOfPosts());
        entity = repository.save(entity);
        return buildUserDto(entity);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.delete(entity);
    }

    private UserDto buildUserDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .amountOfPosts(entity.getAmountOfPosts())
                .build();
    }
}
