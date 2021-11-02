package com.example.server.services.post;

import com.example.server.model.post.Post;
import com.example.server.model.post.PostEntity;

import java.util.List;

public interface PostService {

    Post add (Post post);
    Post getById(String id);
    Post update(String id, Post post);
    List<Post> findAll();
    void deleteById(String id);
    void deleteAll();
}
