package com.example.server.controller;

import com.example.server.exceptions.PostNotFoundException;
import com.example.server.model.Post;
import com.example.server.repository.PostRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostRepository repository;

    @PostMapping("/add")
    public Post add(@RequestBody JsonNode jsonNode) {
        return null;
    }

    @PutMapping("/{id}")
    public Post update(@RequestBody JsonNode jsonNode, @PathVariable String id) {
        if(!repository.existsById(id)){
            throw new PostNotFoundException(id);
        }
        repository.deleteById(id);
        //return userRepository.save(newUser);
        return null;
    }

    @GetMapping("/{id}")
    public Optional<Post> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        if(!repository.existsById(id)){
            throw new PostNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @GetMapping("/all")
    public List<Post> getAll() {
        return repository.findAll();
    }
}
