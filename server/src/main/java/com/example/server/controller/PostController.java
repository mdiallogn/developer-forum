package com.example.server.controller;

import com.example.server.exceptions.PostNotFoundException;
import com.example.server.model.Post;
import com.example.server.model.UserEntity;
import com.example.server.repository.PostRepository;
import com.example.server.repository.UserRepository;
import com.example.server.utils.DateGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostRepository repository;
    private final UserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add/{userid}")

    public ResponseEntity<Post> add(@RequestBody JsonNode jsonNode, @PathVariable String userid) {

        UserEntity author = userRepository.getUserById(userid);
        Post post = mapper.convertValue(jsonNode, Post.class);
        post.setAuthor(author);
        post.setDate(DateGenerator.generateDate());
        post.setComments(new ArrayList<>());
        repository.save(post);

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestBody JsonNode jsonNode, @PathVariable String id) throws JsonProcessingException {
        if(!repository.existsById(id)){
            throw new PostNotFoundException(id);
        }
        Post post = repository.getPostById(id);
        Post newPost = mapper.treeToValue(jsonNode, Post.class);

        UserEntity userEntity = userRepository.getUserById(post.getAuthor().getId());
        newPost.setAuthor(userEntity);
        newPost.setDate(post.getDate());
        System.out.println(newPost);

        repository.deleteById(id);

        return new ResponseEntity<>(repository.save(newPost), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable String id) {
        return new ResponseEntity<>(repository.getPostById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        if(!repository.existsById(id)){
            throw new PostNotFoundException(id);
        }
        repository.deleteById(id);

        return new ResponseEntity<>("Post deleted successfully", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        if(repository.count() > 0){
            repository.deleteAll();
            return new ResponseEntity<>("All posts deleted successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Post repository is empty", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }
}
