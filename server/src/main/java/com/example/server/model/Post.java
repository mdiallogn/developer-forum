package com.example.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class Post{

    @Id
    private String id;
    @Field("subject")
    private String subject;
    @Field("content")
    private String content;
    @Field("author")
    private User author;
    @Field("date")
    private String date;
    @Field("comments")
    private List<Comment> comments;

    public Post(String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.author = null;
        this.date = null;
        this.comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void deleteComment(Comment comment){
        this.comments.remove(comment);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return  getId().equals(post.getId()) &&
                getSubject().equals(post.getSubject()) &&
                getContent().equals(post.getContent()) &&
                getAuthor().equals(post.getAuthor()) &&
                getComments().equals(post.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getContent(), getAuthor(), getComments());
    }
}
