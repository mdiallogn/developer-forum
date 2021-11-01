package com.example.server.services.post;

import com.example.server.exceptions.PostNotFoundException;
import com.example.server.model.post.Post;
import com.example.server.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImplement implements PostService{

    private final PostRepository repository;

    @Override
    public Post add(Post post) {
        return repository.save(post);
    }

    @Override
    public Post getById(String id) {
        if(!repository.existsById(id)){
            throw new PostNotFoundException(id);
        }
        return repository.getPostById(id);
    }

    @Override
    public Post update(String id, Post post) {
        if(!repository.existsById(id)){
            throw new PostNotFoundException(id);
        }
        repository.deleteById(id);
        return repository.save(post);
    }

    @Override
    public List<Post> findAll() {
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
