package com.example.server.model.comment;

import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class CommentEntity implements Comment{

    @Id
    private String id;
    @Field("message")
    private String message;

    @JsonSubTypes({@JsonSubTypes.Type(value = UserEntity.class)})
    @Field("author")
    private UserEntity author;
    @Field("date")
    private String date;
    @Field("reply")
    private List<Comment> reply;

    public CommentEntity(String message) {
        this.message = message;
        this.author = null;
        this.reply = new ArrayList<>();
        this.date = Timestamp.valueOf(LocalDateTime.now()).toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public UserEntity getAuthor() {
        return author;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public List<Comment> getReply() {
        return reply;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Override
    public void setReply(List<Comment> comments){
        this.reply = comments;
    }

    @Override
    public void addReply(Comment comment) {
        this.reply.add(comment);
    }

    @Override
    public void deleteReply(Comment comment){
        this.reply.remove(comment);
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
