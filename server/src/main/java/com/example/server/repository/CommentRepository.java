package com.example.server.repository;

import com.example.server.model.comment.Comment;
import com.example.server.model.comment.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    CommentEntity getCommentById(String id);
}
