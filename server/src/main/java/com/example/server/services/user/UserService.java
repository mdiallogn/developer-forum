package com.example.server.services.user;

import com.example.server.model.user.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity add (UserEntity user);
    UserEntity getById(String id);
    UserEntity update(String id, UserEntity user);
    List<UserEntity> findAll();
    void deleteById(String id);
    void deleteAll();
}
