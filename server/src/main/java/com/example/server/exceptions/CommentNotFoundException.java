package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CommentNotFoundException(String idComment){
        super(String.format("No comment found with id %s ", idComment));
    }
}
