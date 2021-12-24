package com.example.server.controller;

import com.example.server.concurrency.PostLocker;
import com.example.server.model.comment.Comment;
import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.Post;
import com.example.server.model.post.PostEntity;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.services.Vote;
import com.example.server.services.comment.CommentService;
import com.example.server.services.post.PostService;
import com.example.server.services.user.UserService;
import com.example.server.threads.NumberLikeDislikeUpdater;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private PostLocker lockers = new PostLocker();

    @PostMapping("/{userid}")
    public ResponseEntity<Post> add(@RequestBody PostEntity post, @PathVariable String userid) {
        User author = userService.getById(userid);
        PostEntity newPost = new PostEntity(post.getSubject(), post.getContent());
        newPost.setAuthor(author);
        newPost.setContent(post.getContent());
        postService.add(newPost);

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestBody JsonNode jsonNode, @PathVariable String id) throws JsonProcessingException {

        PostEntity post = postService.getById(id);
        PostEntity newPost = mapper.treeToValue(jsonNode, PostEntity.class);

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
        PostEntity post = postService.getById(id);
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
        PostEntity post = postService.getById(postId);
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

        CommentEntity replyTarget = (CommentEntity) parentComment.get();
        replyTarget.addReply(commentService.add(reply));

        PostEntity newPost = postService.update(postId, post);
        return new ResponseEntity<>(newPost, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        postService.deleteById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAll() {
        postService.deleteAll();
        return new ResponseEntity<>("Post repository is empty", HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<PostEntity>> getAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{id}/upvotes")
    public ResponseEntity<Post> upVote(@RequestBody UserEntity voter,
                                       @PathVariable String id) throws InterruptedException {
        // Wait until the post with {id} are on update.
        while (lockers.isAlreadyLocked(id)){}
        lockers.lock(id); //lock post.

        PostEntity post = postService.getById(id);
        if (post == null) {
            throw new IllegalArgumentException("Invalid Post id");
        }
        //Vote vote = new Vote(post.getTotalLikeDislike());
        //initialize our thread for managing the number of likes/dislikes on post
        //Thread upVoteThread = new Thread(new NumberLikeDislikeUpdater(vote, 1,post));
        Optional<UserEntity> result = post
                .getUpVoters()
                .stream()
                .filter(user -> user.getId().equals(voter.getId()))
                .findFirst();
        if (result.isEmpty()) {
            // If user already down vote, cancel down vote before upvote
            Optional<UserEntity> downVoter = post
                    .getDownVoters()
                    .stream()
                    .filter(user -> user.getId().equals(voter.getId()))
                    .findFirst();
            downVoter.ifPresent(post::removeDownVoter);
            post.addUpVoter(voter);
            //upVoteThread.start();//we increment the total number of like/dislike
        } else {
            post.removeUpVoter(voter);//???????
        }
        PostEntity update = postService.update(post.getId(), post);
        lockers.unlock(id);
        return ResponseEntity.ok(update);
    }

    @PostMapping("/{id}/downvotes")
    public ResponseEntity<Post> downVote(@RequestBody UserEntity voter,
                                         @PathVariable String id) throws InterruptedException {
        // Wait until the post with {id} are on update.
        while (lockers.isAlreadyLocked(id)){}
        lockers.lock(id); //lock post.
        PostEntity post = postService.getById(id);
        if (post == null) {
            throw new IllegalArgumentException("Invalid Post id");
        }
        //Vote vote = new Vote(post.getTotalLikeDislike());
        //initialize our thread for managing the number of likes/dislikes on post
       //Thread downVoteThread = new Thread(new NumberLikeDislikeUpdater(vote, 0,post));
        Optional<UserEntity> result = post
                .getDownVoters()
                .stream()
                .filter(user -> user.getId().equals(voter.getId()))
                .findFirst();
        if (result.isEmpty()) {
            // If user already down vote, cancel down vote before upvote
            Optional<UserEntity> upVoter = post
                    .getUpVoters()
                    .stream()
                    .filter(user -> user.getId().equals(voter.getId()))
                    .findFirst();
            upVoter.ifPresent(post::removeUpVoter);
            post.addDownVoter(voter);
            //downVoteThread.start();//we decrement the total number of like/dislike
        } else {
            post.removeDownVoter(voter);
        }
        PostEntity update = postService.update(post.getId(), post);
        lockers.unlock(id);
        return ResponseEntity.ok(update);
    }
}
