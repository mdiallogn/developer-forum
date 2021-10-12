package com.example.server.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class Common {

    private UUID id;
    private String message;
    private IUser author;

    public Common(String message, IUser author) {
        this.id = UUID.randomUUID();
        this.message = message;
        this.author = author;
    }

    public Common(){}

    public UUID getId() {
        return id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IUser getAuthor() {
        return author;
    }

    public void setAuthor(IUser author) {
        this.author = author;
    }

}
