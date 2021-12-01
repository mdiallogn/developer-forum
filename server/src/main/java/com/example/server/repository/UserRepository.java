package com.example.server.repository;


import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    //    UserEntity getUserByUserName(String userName);
    UserEntity findUserEntityByUserName(String userName);

    UserEntity getUserById(String id);

    List<UserEntity> findByUserName(String userName);
}
