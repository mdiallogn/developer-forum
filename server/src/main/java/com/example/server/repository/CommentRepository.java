package com.example.server.repository;

import com.example.server.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

    Comment getCommentById(String id);
}
