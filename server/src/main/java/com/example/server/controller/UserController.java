package com.example.server.controller;

import com.example.server.model.Role;
import com.example.server.model.user.UserEntity;
import com.example.server.repository.UserRepository;
import com.example.server.services.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<UserEntity> add(@RequestBody JsonNode jsonNode) throws JsonProcessingException {
        UserEntity userEntity = mapper.treeToValue(jsonNode, UserEntity.class);
        return new ResponseEntity<>(userService.add(userEntity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable("id") String id, @RequestBody JsonNode jsonNode) throws JsonProcessingException {
        UserEntity userEntity = mapper.treeToValue(jsonNode, UserEntity.class);
        return new ResponseEntity<>(userService.update(id, userEntity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return new ResponseEntity<>("Deleted successfully !", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        userService.deleteAll();
        return  ResponseEntity.ok("All users Deleted successfully");
    }

    //just for testing...
    @PostMapping("/saveUsers")
    public void saveUsers(){
        UserEntity user1=new UserEntity();
        user1.setUserName("mohammed");
        user1.setUserName("kasmi");
        user1.setRole(Role.USER.name());

        UserEntity user2=new UserEntity();
        user1.setUserName("mohammed2");
        user1.setUserName("kasmi2");
        user1.setRole(Role.USER.name());
        userRepository.save(user1);
        userRepository.save(user2);

    }
}
