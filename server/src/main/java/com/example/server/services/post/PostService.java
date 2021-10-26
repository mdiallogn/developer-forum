package com.example.server.services.post;

import com.example.server.model.post.PostEntity;

import java.util.List;

public interface PostService {

    PostEntity add (PostEntity post);
    PostEntity getById(String id);
    PostEntity update(String id, PostEntity post);
    List<PostEntity> findAll();
    void deleteById(String id);
    void deleteAll();
}
