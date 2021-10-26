package com.example.server.repository;


import com.example.server.model.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity getUserByUserName(String userName);
    UserEntity getUserById(String id);
    UserEntity getUserEntityByUserName(String username);
}
