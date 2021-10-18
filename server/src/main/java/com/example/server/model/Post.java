package com.example.server.model;

import lombok.*;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class Post extends Common{

    private String subject;
    private List<Comment> comments;

    public Post(String subject, String message, IUser author) {
        super(message, author);
        this.subject = subject;
        this.comments = new ArrayList<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "subject='" + subject + '\'' +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        if (!super.equals(o)) return false;
        Post post = (Post) o;
        return  getSubject().equals(post.getSubject()) &&
                getComments().equals(post.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSubject(), getComments());
    }
}
