package com.example.server.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "comment")
public class Comment extends Common{

    List<Comment> reply;

    public Comment(String message, IUser author) {
        super(message, author);
        this.reply = new ArrayList<>();
    }

    public List<Comment> getReply() {
        return reply;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + this.getAuthor() +'\''+
                "reply=" + reply +
                '}';
    }
}
