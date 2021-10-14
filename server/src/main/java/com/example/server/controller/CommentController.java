package com.example.server.controller;

import com.example.server.exceptions.CommentNotFoundException;
import com.example.server.model.Comment;
import com.example.server.repository.CommentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentRepository repository;

    @PostMapping("/add")
    public Comment add(@RequestBody JsonNode jsonNode) {
        return null;
    }

    @PutMapping("/{id}")
    public Comment update(@RequestBody JsonNode jsonNode, @PathVariable String id) {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException(id);
        }
        repository.deleteById(id);
        //return userRepository.save(newUser);
        return null;
    }

    @GetMapping("/{id}")
    public Optional<Comment> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public List<Comment> getAll() {
        return repository.findAll();
    }
}
