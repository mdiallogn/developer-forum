package com.example.server.controller;

import com.example.server.model.AuthRequest;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.services.user.UserService;
import com.example.server.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody UserEntity user) throws JsonProcessingException {
        Logger logger = LoggerFactory.getLogger(UserController.class);
        logger.info("user :: "  + user.toString());
        if (user != null && userService.getByUsername(user.getUserName()) != null) {
            return new ResponseEntity<>("this user name is already taken ", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.add(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
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
    public ResponseEntity<?> getAllUsers() {
        //@RequestParam @Nullable String username
        /*
        if (username != null && !username.isEmpty()) {
            var user = userService.getByUsername(username);
            List<User> users = new ArrayList<>();
            if (user != null) {
                users.add(user);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        }*/
        return userService.findAll() != null ?
                new ResponseEntity<>(userService.findAll(), HttpStatus.OK) :
                ResponseEntity.ok("The user repository is empty");
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.ok("All users Deleted successfully");
    }
}
