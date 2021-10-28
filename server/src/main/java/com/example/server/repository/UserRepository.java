package com.example.server.repository;


import com.example.server.model.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity getUserByUserName(String userName);
    UserEntity getUserById(String id);
    UserEntity getUserEntityByUserName(String username);
}
