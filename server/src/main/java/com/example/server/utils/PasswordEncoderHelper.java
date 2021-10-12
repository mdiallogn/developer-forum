package com.example.server.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderHelper {


    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
