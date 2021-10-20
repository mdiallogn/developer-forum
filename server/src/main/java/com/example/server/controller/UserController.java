package com.example.server.controller;

import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.UserEntity;
import com.example.server.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     *
     * @param jsonNode
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/add")
    public ResponseEntity<UserEntity> add(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        UserEntity userEntity = mapper.treeToValue(jsonNode, UserEntity.class);
        return new ResponseEntity<>(userRepository.save(userEntity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable("id") String id, @RequestBody JsonNode jsonNode) throws JsonProcessingException {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        UserEntity userEntity = mapper.treeToValue(jsonNode, UserEntity.class);
        return new ResponseEntity<>(userRepository.save(userEntity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable("id") String id) {
        System.out.println("id : "+id);
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>("Deleted successfully !", HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        if(userRepository.count() > 0){
            userRepository.deleteAll();
        }
        return  ResponseEntity.ok("All users Deleted successfully");
    }
}
