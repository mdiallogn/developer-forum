package com.example.server.services;

import com.example.server.model.*;

import java.util.List;
import java.util.Optional;

public interface Services {

    UserEntity addUser(UserEntity userEntity);
    UserEntity updateUser(String idUser, UserEntity newData);
    Optional<UserEntity> getUserById(String idUser);
    void deleteUser(String idUser);
    List<UserEntity> getUserList();

    Post addPost(Post post);
    Post updatePost(String idPost, Post post);
    Optional<Post> getPostById(String idPost);
    void deletePost(String idPost);
    List<Post> getPostList();

    Comment addComment(Comment comment);
    Comment updateComment(String idComment, Comment comment);
    Optional<Comment> getCommentById(String idComment);
    void deleteComment(String idComment);
    List<Comment> getCommentList();
}
