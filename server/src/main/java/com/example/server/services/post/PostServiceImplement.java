package com.example.server.services.post;

import com.example.server.exceptions.PostNotFoundException;
import com.example.server.model.post.PostEntity;
import com.example.server.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImplement implements PostService{

    private final PostRepository repository;

    @Override
    public PostEntity add(PostEntity post) {
        return repository.save(post);
    }

    @Override
    public PostEntity getById(String id) {
        return repository.getPostById(id);
    }

    @Override
    public PostEntity update(String id, PostEntity post) {
        if(!repository.existsById(id)){
            throw new PostNotFoundException(id);
        }
        repository.deleteById(id);
        return repository.save(post);
    }

    @Override
    public List<PostEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
            if(!repository.existsById(id)){
                throw new PostNotFoundException(id);
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
