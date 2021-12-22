package com.example.server.controller;

import com.example.server.model.Notification;
import com.example.server.model.comment.Comment;
import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.Post;
import com.example.server.model.post.PostEntity;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.repository.NotificationRepository;
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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/add/{postId}/{userid}")
    public ResponseEntity<Comment> add(@RequestBody JsonNode jsonNode, @PathVariable String postId,
                                       @PathVariable String userid) throws JsonProcessingException {

        UserEntity author = (UserEntity) userService.getById(userid);
        Post post = postService.getById(postId);
        Notification notification = new Notification(author.getUserName()+ " commented on your post");
        repository.save(notification);
        post.getAuthor().getNotifications().add(notification);
        this.sendCommentNotification(post.getAuthor().getUserName(), author.getUserName());
        Comment comment = mapper.treeToValue(jsonNode, CommentEntity.class);

        comment.setDate(DateGenerator.generateDate());
        comment.setAuthor(author);
        comment.setReply(new ArrayList<>());


        post.addComment(comment);

        commentService.add(comment);

        postService.add(post);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<Comment> update(@RequestBody JsonNode jsonNode, @PathVariable String postId, @PathVariable String commentId) throws JsonProcessingException {

        Comment comment = commentService.getById(commentId);
        Post post = postService.getById(postId);

        CommentEntity newComment = mapper.treeToValue(jsonNode, CommentEntity.class);

        comment.setMessage(newComment.getMessage());
        post.addComment(comment);

        postService.update(postId, post);

        return new ResponseEntity<>(comment, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable String id) {
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable String postId, @PathVariable String commentId) {

        Post post = postService.getById(postId);
        Comment comment = commentService.getById(commentId);

        post.deleteComment(comment);

        commentService.deleteById(commentId);
        postService.update(postId, post);

        return ResponseEntity.ok("Comment deleted successfully !");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAll() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll(){
        commentService.deleteAll();
        return new ResponseEntity<>("Comment repository is empty", HttpStatus.NOT_FOUND);
    }

    @MessageMapping("/notification")
    public void sendCommentNotification(@Payload String postAuthorUserName, String commentAuthorUsername){
        this.messagingTemplate.convertAndSend("/users/"+postAuthorUserName+"/notifications", commentAuthorUsername+" commented on your post ");
    }
}
