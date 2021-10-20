package com.example.server.services;

import com.example.server.exceptions.PostNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Comment;
import com.example.server.model.Post;
import com.example.server.model.UserEntity;
import com.example.server.repository.CommentRepository;
import com.example.server.repository.PostRepository;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ServiceImpl implements Services{

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUser(String idUser, UserEntity newUserEntity) {
            if(!userRepository.existsById(idUser)){
               throw new UserNotFoundException(idUser);
            }
            userRepository.deleteById(idUser);
        return userRepository.save(newUserEntity);
    }

    @Override
    public Optional<UserEntity> getUserById(String idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public void deleteUser(String idUser) {
        if(!userRepository.existsById(idUser)){
            throw new UserNotFoundException(idUser);
        }
        userRepository.deleteById(idUser);
    }

    @Override
    public List<UserEntity> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(String idPost, Post post) {
        if(!postRepository.existsById(idPost)){
           throw  new PostNotFoundException(idPost);
        }
        postRepository.deleteById(idPost);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(String idPost) {
        return postRepository.findById(idPost);
    }

    @Override
    public void deletePost(String idPost) {
        if(!postRepository.existsById(idPost)){
            throw new PostNotFoundException(idPost);
        }
        postRepository.deleteById(idPost);
    }

    @Override
    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(String idComment, Comment comment) {
        if(commentRepository.existsById(idComment)){
            commentRepository.deleteById(idComment);
        }
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(String idComment) {
        return commentRepository.findById(idComment);
    }

    @Override
    public void deleteComment(String idComment) {
        if(commentRepository.existsById(idComment)){
            commentRepository.deleteById(idComment);
        }
    }

    @Override
    public List<Comment> getCommentList() {
        return commentRepository.findAll();
    }
}
