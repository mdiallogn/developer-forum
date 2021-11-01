package com.example.server.services.user;

import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;

import java.util.List;

public interface UserService {

    User add (User user);
    User getById(String id);
    User update(String id, User user);
    List<User> findAll();
    void deleteById(String id);
    void deleteAll();
}
