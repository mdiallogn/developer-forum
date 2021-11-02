package com.example.server.services.comment;

import com.example.server.model.comment.Comment;
import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.PostEntity;

import java.util.List;

public interface CommentService {

    Comment add (Comment post);
    Comment getById(String id);
    Comment update(String id, Comment post);
    List<Comment> findAll();
    void deleteById(String id);
    void deleteAll();

}
