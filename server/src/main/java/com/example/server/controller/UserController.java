package com.example.server.controller;

import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.User;
import com.example.server.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add")
    public User add(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        User user = mapper.treeToValue(jsonNode, User.class);
        repository.save(user);
        return user;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody JsonNode jsonNode) throws JsonProcessingException {
        if(!repository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
        User user = mapper.treeToValue(jsonNode, User.class);
        return new ResponseEntity<>(repository.save(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String id) {
        System.out.println("id : "+id);
        Optional<User> user = repository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        if(!repository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
        return new ResponseEntity<>("Deleted successfully !", HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return repository.findAll();
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        if(repository.count() > 0){
            repository.deleteAll();
        }
        return  ResponseEntity.ok("All users Deleted successfully");
    }
}
