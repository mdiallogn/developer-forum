package com.example.server.config.security;

import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.example.server.utils.Const;
import com.example.server.utils.Encoders;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final List<User> users = Stream.of(
            new UserEntity("Diallo", "Mamadou", "greer", "Greer", Const.ADMIN),
            new UserEntity("Dupont", "Jean Pierre", "jdupont", "Jdupont", Const.USER),
            new UserEntity("Poulain", "Emanuel", "epoulain", "Epoulain", Const.USER)
    ).collect(Collectors.toList());

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User user =  new UserEntity("Diallo", "Mamadou", "greer", "Greer", Const.ADMIN);
        auth.inMemoryAuthentication().withUser(user.getUserName())
                .password(Encoders.userPasswordEncoder().encode(user.getPassword()))
                .roles(user.getRole());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole(Const.ADMIN)
                .antMatchers("/api/home").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/api/post/**").permitAll()
                .antMatchers("/api/comment/**").permitAll()
                .antMatchers("/api/login*").permitAll()
                .antMatchers("/api").permitAll()
                .anyRequest().authenticated();

    }
}
