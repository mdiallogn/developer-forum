package com.example.server.controller;

import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.PostEntity;
import com.example.server.model.user.UserEntity;
import com.example.server.services.comment.CommentServiceImplement;
import com.example.server.services.post.PostServiceImplement;
import com.example.server.services.user.UserServiceImplement;
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

    private final CommentServiceImplement commentServiceImplement;
    private final UserServiceImplement userServiceImplement;
    private final PostServiceImplement postServiceImplement;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add/{postId}/{userid}")
    public ResponseEntity<CommentEntity> add(@RequestBody JsonNode jsonNode, @PathVariable String postId,
                                             @PathVariable String userid) throws JsonProcessingException {

        UserEntity author = userServiceImplement.getById(userid);
        PostEntity postEntity = postServiceImplement.getById(postId);

        CommentEntity commentEntity = mapper.treeToValue(jsonNode, CommentEntity.class);

        commentEntity.setDate(DateGenerator.generateDate());
        commentEntity.setAuthor(author);
        commentEntity.setReply(new ArrayList<>());

        postEntity.addComment(commentEntity);

        commentServiceImplement.add(commentEntity);

        postServiceImplement.add(postEntity);

        return new ResponseEntity<>(commentEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<CommentEntity> update(@RequestBody JsonNode jsonNode, @PathVariable String postId, @PathVariable String commentId) throws JsonProcessingException {

        CommentEntity commentEntity = commentServiceImplement.getById(commentId);
        PostEntity postEntity = postServiceImplement.getById(postId);

        CommentEntity newCommentEntity = mapper.treeToValue(jsonNode, CommentEntity.class);

        commentEntity.setMessage(newCommentEntity.getMessage());
        postEntity.addComment(commentEntity);

        postServiceImplement.update(postId, postEntity);

        return new ResponseEntity<>(commentEntity, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentEntity> getById(@PathVariable String id) {
        return new ResponseEntity<>(commentServiceImplement.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable String postId, @PathVariable String commentId) {

        PostEntity postEntity = postServiceImplement.getById(postId);
        CommentEntity commentEntity = commentServiceImplement.getById(commentId);

        postEntity.deleteComment(commentEntity);

        commentServiceImplement.deleteById(commentId);
        postServiceImplement.update(postId, postEntity);

        return ResponseEntity.ok("Comment deleted successfully !");
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentEntity>> getAll() {
        return new ResponseEntity<>(commentServiceImplement.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        commentServiceImplement.deleteAll();
        return new ResponseEntity<>("Comment repository is empty", HttpStatus.NOT_FOUND);
    }
}
