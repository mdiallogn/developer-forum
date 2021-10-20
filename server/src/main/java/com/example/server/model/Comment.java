package com.example.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    @Field("message")
    private String message;
    @Field("author")
    private UserEntity author;
    @Field("date")
    private String date;
    @Field("reply")
    private List<Comment> reply;

    public Comment(String message) {
        this.message = message;
        this.author = null;
        this.reply = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Comment> getReply() {
        return reply;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public void addReply(Comment comment){
        this.reply.add(comment);
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
