package com.example.server.services.comment;

import com.example.server.model.comment.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService{
    @Override
    public CommentEntity add(CommentEntity post) {
        return null;
    }

    @Override
    public CommentEntity getById(String id) {
        return null;
    }

    @Override
    public CommentEntity update(String id, CommentEntity post) {
        return null;
    }

    @Override
    public List<CommentEntity> findAll() {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteAll() {

    }
}
