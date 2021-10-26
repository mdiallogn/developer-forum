package com.example.server.services.comment;

import com.example.server.model.comment.CommentEntity;
import com.example.server.model.post.PostEntity;

import java.util.List;

public interface CommentService {

    CommentEntity add (CommentEntity post);
    CommentEntity getById(String id);
    CommentEntity update(String id, CommentEntity post);
    List<CommentEntity> findAll();
    void deleteById(String id);
    void deleteAll();

}
