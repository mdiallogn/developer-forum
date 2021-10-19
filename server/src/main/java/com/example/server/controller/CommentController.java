package com.example.server.controller;

import com.example.server.exceptions.CommentNotFoundException;
import com.example.server.model.Comment;
import com.example.server.model.Post;
import com.example.server.model.User;
import com.example.server.repository.CommentRepository;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add/{postId}/{userid}")
    public ResponseEntity<Comment> add(@RequestBody JsonNode jsonNode, @PathVariable String postId,
                                      @PathVariable String userid) throws JsonProcessingException {

        User author = userRepository.getUserById(userid);
        Post post = postRepository.getPostById(postId);

        Comment comment = mapper.treeToValue(jsonNode, Comment.class);
        comment.setDate(DateGenerator.generateDate());
        comment.setAuthor(author);
        comment.setReply(new ArrayList<>());
        post.addComment(repository.save(comment));

        postRepository.deleteById(postId);
        postRepository.save(post);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<Comment> update(@RequestBody JsonNode jsonNode, @PathVariable String postId, @PathVariable String commentId) {
        if(!repository.existsById(commentId)){
            throw new CommentNotFoundException(commentId);
        }

        Comment comment = repository.getCommentById(commentId);
        Post post = postRepository.getPostById(postId);
        post.deleteComment(comment);


        postRepository.save(post);
        postRepository.deleteById(postId);

        repository.deleteById(commentId);

        /**
         * not ended yet
         */

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> getById(@PathVariable String id) {
        if(!repository.existsById(id)) throw new CommentNotFoundException(id);

        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable String postId, @PathVariable String commentId) {
        if(!repository.existsById(commentId)){
            throw new CommentNotFoundException(commentId);
        }

        Post post = postRepository.getPostById(postId);
        Comment comment = repository.getCommentById(commentId);

        post.deleteComment(comment);
        repository.deleteById(commentId);

        postRepository.deleteById(postId);
        postRepository.save(post);

        return ResponseEntity.ok("Comment deleted successfully !");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        if(repository.count() > 0){
            repository.deleteAll();
            return new ResponseEntity<>("All comments deleted successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Comment repository is empty", HttpStatus.NOT_FOUND);
    }
}
