package com.example.server;

import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class})
@EnableMongoRepositories
@SpringBootApplication
public class ServerApplication {

	@Autowired
	private UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
