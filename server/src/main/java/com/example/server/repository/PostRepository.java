package com.example.server.repository;

import com.example.server.model.post.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<PostEntity, String> {

    PostEntity getPostById(String id);
}
