package com.example.server.controller;

import com.example.server.exceptions.CommentNotFoundException;
import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.PostEntity;
import com.example.server.model.user.UserEntity;
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
    public ResponseEntity<CommentEntity> add(@RequestBody JsonNode jsonNode, @PathVariable String postId,
                                             @PathVariable String userid) throws JsonProcessingException {

        UserEntity author = userRepository.getUserById(userid);
        PostEntity postEntity = postRepository.getPostById(postId);

        CommentEntity commentEntity = mapper.treeToValue(jsonNode, CommentEntity.class);
        commentEntity.setDate(DateGenerator.generateDate());
        commentEntity.setAuthor(author);
        commentEntity.setReply(new ArrayList<>());
        postEntity.addComment(repository.save(commentEntity));

        postRepository.deleteById(postId);
        postRepository.save(postEntity);

        return new ResponseEntity<>(commentEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentEntity> update(@RequestBody JsonNode jsonNode, @PathVariable String postId, @PathVariable String commentId) throws JsonProcessingException {
        if(!repository.existsById(commentId)){
            throw new CommentNotFoundException(commentId);
        }

        CommentEntity commentEntity = repository.getCommentById(commentId);
        PostEntity postEntity = postRepository.getPostById(postId);
        //post.deleteComment(comment);
        CommentEntity newCommentEntity = mapper.treeToValue(jsonNode, CommentEntity.class);

        commentEntity.setMessage(newCommentEntity.getMessage());

        postRepository.save(postEntity);
        postRepository.deleteById(postId);

        repository.deleteById(commentId);

        /**
         * not ended yet
         */

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CommentEntity>> getById(@PathVariable String id) {
        if(!repository.existsById(id)) throw new CommentNotFoundException(id);

        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable String postId, @PathVariable String commentId) {
        if(!repository.existsById(commentId)){
            throw new CommentNotFoundException(commentId);
        }

        PostEntity postEntity = postRepository.getPostById(postId);
        CommentEntity commentEntity = repository.getCommentById(commentId);

        postEntity.deleteComment(commentEntity);
        repository.deleteById(commentId);

        postRepository.deleteById(postId);
        postRepository.save(postEntity);

        return ResponseEntity.ok("Comment deleted successfully !");
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentEntity>> getAll() {
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
