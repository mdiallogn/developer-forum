package com.example.server.services.comment;

import com.example.server.exceptions.CommentNotFoundException;
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
    public CommentEntity add(CommentEntity post) {
        return repository.save(post);
    }

    @Override
    public CommentEntity getById(String id) {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException(id);
        }
        return repository.getCommentById(id);
    }

    @Override
    public CommentEntity update(String id, CommentEntity comment) {
        this.deleteById(id);
        return this.add(comment);
    }

    @Override
    public List<CommentEntity> findAll() {
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
