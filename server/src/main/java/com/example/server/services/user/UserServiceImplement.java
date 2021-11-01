package com.example.server.services.user;

import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService{

    private final UserRepository repository;

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    @Override
    public UserEntity getById(String id) {
        if(!repository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        return repository.getUserById(id);
    }

    @Override
    public User update(String id, User user) {
        if(!repository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        if(!repository.existsById(id)){
            throw new UserNotFoundException(id);
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
