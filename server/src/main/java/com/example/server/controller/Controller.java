package com.example.server.controller;

import com.example.server.model.Comment;
import com.example.server.model.Post;
import com.example.server.model.User;
import com.example.server.services.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.server.utils.Const.*;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final ServiceImpl service;

    @GetMapping(HOME)
    public ResponseEntity<String> homePage(){
        return ResponseEntity.ok("welcome on board");
    }

    @PostMapping(ADD_USER)
    public User addUser(@RequestBody JsonNode jsonNode){
        return null;
    }
    @PostMapping(ADD_COMMENT)
    public Comment addComment(@RequestBody JsonNode jsonNode){
        return null;
    }
    @PostMapping(ADD_POST)
    public Post addPost(@RequestBody JsonNode jsonNode){
        return null;
    }

    @GetMapping(GET_USER)
    public Optional<User> getUserById(@PathVariable String id){
        return service.getUserById(id);
    }
    @GetMapping(GET_COMMENT)
    public Optional<Comment> getCommentById(@PathVariable String id){
        return service.getCommentById(id);
    }
    @GetMapping(GET_POST)
    public Optional<Post> getPostById(@PathVariable String id){
        return service.getPostById(id);
    }

    @DeleteMapping(DELETE_USER)
    public void deleteUser(@PathVariable String id){
        service.deleteUser(id);
    }
    @DeleteMapping(DELETE_COMMENT)
    public void deleteComment(@PathVariable String id){
        service.deleteComment(id);
    }
    @DeleteMapping(DELETE_POST)
    public void deletePost(@PathVariable String id){
        service.deletePost(id);
    }

    @PutMapping(UPDATE_USER)
    public User updateUser(@PathVariable String id, @PathVariable JsonNode jsonNode){
        return null;
    }
    @PutMapping(UPDATE_COMMENT)
    public Comment updateComment(@PathVariable String id, @PathVariable JsonNode jsonNode){
        return null;
    }
    @PutMapping(UPDATE_POST)
    public Post updatePost(@PathVariable String id, @PathVariable JsonNode jsonNode){
        return null;
    }

    @GetMapping(GET_USER_LIST)
    public List<User> getUserList(){
        return service.getUserList();
    }
    @GetMapping(GET_COMMENT_LIST)
    public List<Comment> getCommentList(){
        return service.getCommentList();
    }
    @GetMapping(GET_POST_LIST)
    public List<Post> getPostList(){
        return service.getPostList();
    }
}
