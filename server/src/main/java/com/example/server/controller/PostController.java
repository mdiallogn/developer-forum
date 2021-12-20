package com.example.server.controller;

import com.example.server.model.comment.Comment;
import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.Post;
import com.example.server.model.post.PostEntity;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.services.comment.CommentService;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/{userid}")

    public ResponseEntity<Post> add(@RequestBody PostEntity post, @PathVariable String userid) {
        User author = userService.getById(userid);
        Post newPost = new PostEntity(post.getSubject(), post.getContent());
        newPost.setAuthor(author);
        newPost.setContent(post.getContent());
        postService.add(newPost);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
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

    @PostMapping("/{id}/comments")
    public ResponseEntity<Post> comment(@RequestBody CommentEntity comment, @PathVariable String id) {
        Post post =  postService.getById(id);
        if (post == null) {
            throw new IllegalArgumentException("Invalid Post id");
        }
        CommentEntity newComment = new CommentEntity(comment.getMessage());
        newComment.setAuthor(comment.getAuthor());
        post.addComment(commentService.add(newComment));
        Post newPost = postService.update(id, post);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments/{commentId}/replies")
    public ResponseEntity<Post> reply(@RequestBody CommentEntity comment,
                                      @PathVariable String postId,
                                      @PathVariable String commentId) {
        Post post =  postService.getById(postId);
        if (post == null) {
            throw new IllegalArgumentException("Invalid Post id");
        }
        Optional<Comment> parentComment = post.getComments()
                                                    .stream()
                                                    .filter(c -> commentId.equals(c.getId()))
                                                    .findFirst();
        if (parentComment.isEmpty()) {
            throw new IllegalArgumentException("Parent comment not found !");
        }

        CommentEntity reply = new CommentEntity(comment.getMessage());
        reply.setAuthor(comment.getAuthor());

        CommentEntity replyTarget = (CommentEntity)parentComment.get();
        replyTarget.addReply(commentService.add(reply));

        Post newPost = postService.update(postId, post);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
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

    @GetMapping()
    public ResponseEntity<List<Post>> getAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }
}
