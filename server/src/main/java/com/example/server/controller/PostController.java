package com.example.server.controller;

import com.example.server.model.post.Post;
import com.example.server.model.post.PostEntity;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.services.post.PostService;
import com.example.server.services.post.PostServiceImplement;
import com.example.server.services.user.UserService;
import com.example.server.services.user.UserServiceImplement;
import com.example.server.utils.DateGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add/{userid}")

    public ResponseEntity<Post> add(@RequestBody JsonNode jsonNode, @PathVariable String userid) {

        User author = userService.getById(userid);
        Post post = mapper.convertValue(jsonNode, PostEntity.class);
        post.setAuthor(author);
        post.setDate(DateGenerator.generateDate());
        post.setComments(new ArrayList<>());

        postService.add(post);

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestBody JsonNode jsonNode, @PathVariable String id) throws JsonProcessingException {

        Post post = postService.getById(id);
        Post newPost = mapper.treeToValue(jsonNode, PostEntity.class);

        User userEntity = userService.getById(post.getAuthor().getId());
        newPost.setAuthor(userEntity);
        newPost.setDate(post.getDate());

        postService.update(id, newPost);

        return new ResponseEntity<>(newPost, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable String id) {
        return new ResponseEntity<>(postService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        postService.deleteById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        postService.deleteAll();
        return new ResponseEntity<>("Post repository is empty", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }
}
