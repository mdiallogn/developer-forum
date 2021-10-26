package com.example.server.model.comment;

import com.example.server.model.user.UserEntity;
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
public class CommentEntity implements Comment{

    @Id
    private String id;
    @Field("message")
    private String message;
    @Field("author")
    private UserEntity author;
    @Field("date")
    private String date;
    @Field("reply")
    private List<CommentEntity> reply;

    public CommentEntity(String message) {
        this.message = message;
        this.author = null;
        this.reply = new ArrayList<>();
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
    public List<CommentEntity> getReply() {
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
    public void addReply(CommentEntity commentEntity){
        this.reply.add(commentEntity);
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
        CommentEntity commentEntity = (CommentEntity) o;
        return  getId().equals(commentEntity.getId()) &&
                getMessage().equals(commentEntity.getMessage()) &&
                getAuthor().equals(commentEntity.getAuthor()) &&
                getReply().equals(commentEntity.getReply());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessage(), getAuthor(), getReply());
    }
}
