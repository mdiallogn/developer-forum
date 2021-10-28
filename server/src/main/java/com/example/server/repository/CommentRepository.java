package com.example.server.repository;

import com.example.server.model.comment.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {

    CommentEntity getCommentById(String id);
}
