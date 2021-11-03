package com.example.server.controller;

import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();


    @PostMapping()
    public ResponseEntity<User> add(@RequestBody UserEntity user) throws JsonProcessingException {
        return new ResponseEntity<>(userService.add(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody UserEntity user) throws JsonProcessingException {
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return new ResponseEntity<>("Deleted successfully !", HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll(){
        userService.deleteAll();
        return  ResponseEntity.ok("All users Deleted successfully");
    }
}
