package com.example.server.controller;

import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserRepository repository;

    @PostMapping("/add")
    public User add(@RequestBody JsonNode jsonNode) {
        return null;
    }

    @PutMapping("/{id}")
    public User update(@PathVariable String idUser, @RequestBody JsonNode jsonNode) {
        if(!repository.existsById(idUser)){
            throw new UserNotFoundException(idUser);
        }
        repository.deleteById(idUser);
        //return userRepository.save(newUser);
        return null;
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        if(!repository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public List<User> getAll() {
        return repository.findAll();
    }
}
