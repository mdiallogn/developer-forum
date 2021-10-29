package com.example.server.controller;

import com.example.server.model.post.PostEntity;
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

    public ResponseEntity<PostEntity> add(@RequestBody JsonNode jsonNode, @PathVariable String userid) {

        UserEntity author = userService.getById(userid);
        PostEntity postEntity = mapper.convertValue(jsonNode, PostEntity.class);
        postEntity.setAuthor(author);
        postEntity.setDate(DateGenerator.generateDate());
        postEntity.setCommentEntities(new ArrayList<>());

        postService.add(postEntity);

        return new ResponseEntity<>(postEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostEntity> update(@RequestBody JsonNode jsonNode, @PathVariable String id) throws JsonProcessingException {

        PostEntity postEntity = postService.getById(id);
        PostEntity newPostEntity = mapper.treeToValue(jsonNode, PostEntity.class);

        UserEntity userEntity = userService.getById(postEntity.getAuthor().getId());
        newPostEntity.setAuthor(userEntity);
        newPostEntity.setDate(postEntity.getDate());

        postService.update(id, newPostEntity);

        return new ResponseEntity<>(newPostEntity, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getById(@PathVariable String id) {
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
    public ResponseEntity<List<PostEntity>> getAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }
}
