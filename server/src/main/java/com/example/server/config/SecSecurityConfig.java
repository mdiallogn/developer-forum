package com.example.server.config;

import com.example.server.model.IUser;
import com.example.server.model.User;
import com.example.server.utils.Const;
import com.example.server.utils.PasswordEncoderHelper;
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
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    private final List<IUser> users = Stream.of(
            new User("Diallo", "Mamadou", "greer", "Greer", Const.ADMIN),
            new User("Dupont", "Jean Pierre", "jdupont", "Jdupont", Const.USER),
            new User("Poulain", "Emanuel", "epoulain", "Epoulain", Const.USER)
    ).collect(Collectors.toList());

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User user =  new User("Diallo", "Mamadou", "greer", "Greer", Const.ADMIN);
        auth.inMemoryAuthentication().withUser(user.getUserName())
                .password(PasswordEncoderHelper.passwordEncoder().encode(user.getPassword()))
                .roles(user.getRoles());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole(Const.ADMIN)
                .antMatchers("/api/home").permitAll()
                .antMatchers("/api/user/add").permitAll()
                .antMatchers("/api/user/**").permitAll()
                //.antMatchers("/api/login*").permitAll()
                .anyRequest().authenticated();

    }

}
