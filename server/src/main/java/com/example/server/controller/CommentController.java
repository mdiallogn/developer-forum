package com.example.server.controller;

import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.PostEntity;
import com.example.server.model.user.UserEntity;
import com.example.server.services.comment.CommentService;
import com.example.server.services.post.PostService;
import com.example.server.services.user.UserService;
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
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add/{postId}/{userid}")
    public ResponseEntity<CommentEntity> add(@RequestBody JsonNode jsonNode, @PathVariable String postId,
                                             @PathVariable String userid) throws JsonProcessingException {

        UserEntity author = userService.getById(userid);
        PostEntity postEntity = postService.getById(postId);

        CommentEntity commentEntity = mapper.treeToValue(jsonNode, CommentEntity.class);

        commentEntity.setDate(DateGenerator.generateDate());
        commentEntity.setAuthor(author);
        commentEntity.setReply(new ArrayList<>());

        postEntity.addComment(commentEntity);

        commentService.add(commentEntity);

        postService.add(postEntity);

        return new ResponseEntity<>(commentEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentEntity> update(@RequestBody JsonNode jsonNode, @PathVariable String postId, @PathVariable String commentId) throws JsonProcessingException {

        CommentEntity commentEntity = commentService.getById(commentId);
        PostEntity postEntity = postService.getById(postId);

        CommentEntity newCommentEntity = mapper.treeToValue(jsonNode, CommentEntity.class);

        commentEntity.setMessage(newCommentEntity.getMessage());
        postEntity.addComment(commentEntity);

        postService.update(postId, postEntity);

        return new ResponseEntity<>(commentEntity, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentEntity> getById(@PathVariable String id) {
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable String postId, @PathVariable String commentId) {

        PostEntity postEntity = postService.getById(postId);
        CommentEntity commentEntity = commentService.getById(commentId);

        postEntity.deleteComment(commentEntity);

        commentService.deleteById(commentId);
        postService.update(postId, postEntity);

        return ResponseEntity.ok("Comment deleted successfully !");
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentEntity>> getAll() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        commentService.deleteAll();
        return new ResponseEntity<>("Comment repository is empty", HttpStatus.NOT_FOUND);
    }
}
