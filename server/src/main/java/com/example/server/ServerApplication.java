package com.example.server;

import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ServerApplication {

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void initUsers() {
		List<User> users = Stream.of(
				new UserEntity("mohammed","kasmi", "kasmi", "kasmi1997", "ADMIN"),
				new UserEntity("mohammed","kasmi", "user1", "pwd1", "USER"),
				new UserEntity("mohammed","kasmi", "user2", "pwd2", "USER"),
				new UserEntity("mohammed","kasmi", "user3", "pwd3", "USER")
		).collect(Collectors.toList());
		repository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@PreDestroy
	public void emptyDataBase(){
		repository.deleteAll();
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer(){
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowedMethods("*")
//						.allowCredentials(true);
//			}
//		};
//	}
}
