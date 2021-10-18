package com.example.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class Comment{

    @Id
    private String id;
    private String message;
    private User author;
    private List<Comment> reply;

    public Comment(User author, String message) {
        this.author = author;
        this.message = message;
        this.reply = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getReply() {
        return reply;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", author=" + author +
                ", reply=" + reply +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return  getId().equals(comment.getId()) &&
                getMessage().equals(comment.getMessage()) &&
                getAuthor().equals(comment.getAuthor()) &&
                getReply().equals(comment.getReply());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessage(), getAuthor(), getReply());
    }
}
