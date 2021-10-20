package com.example.server.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encoders {



    public static PasswordEncoder oauthClientPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    public static PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
