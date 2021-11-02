package com.example.server.repository;

import com.example.server.model.post.Post;
import com.example.server.model.post.PostEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Post getPostById(String id);
}
