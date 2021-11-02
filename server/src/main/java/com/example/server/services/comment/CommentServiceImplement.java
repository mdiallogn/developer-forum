package com.example.server.services.comment;

import com.example.server.exceptions.CommentNotFoundException;
import com.example.server.model.comment.Comment;
import com.example.server.model.comment.CommentEntity;
import com.example.server.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService{

    private final CommentRepository repository;
    @Override
    public Comment add(Comment post) {
        return repository.save(post);
    }

    @Override
    public Comment getById(String id) {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException(id);
        }
        return repository.getCommentById(id);
    }

    @Override
    public Comment update(String id, Comment comment) {
        this.deleteById(id);
        return this.add(comment);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        if(repository.count() > 0){
            repository.deleteAll();
        }
    }
}
