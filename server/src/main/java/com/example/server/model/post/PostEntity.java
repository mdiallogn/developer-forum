package com.example.server.model.post;

import com.example.server.model.comment.CommentEntity;
import com.example.server.model.user.UserEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class PostEntity implements Post{

    @Id
    private String id;
    @Field("subject")
    private String subject;
    @Field("content")
    private String content;
    @Field("author")
    private UserEntity author;
    @Field("date")
    private String date;
    @Field("comments")
    private List<CommentEntity> commentEntities;

    public PostEntity(String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.author = null;
        this.date = null;
        this.commentEntities = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getContent() {
        return content;
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
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Override
    public void setCommentEntities(List<CommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
    }

    @Override
    public List<CommentEntity> getCommentEntities() {
        return commentEntities;
    }

    @Override
    public void addComment(CommentEntity commentEntity){
        this.commentEntities.add(commentEntity);
    }

    @Override
    public void deleteComment(CommentEntity commentEntity){
        this.commentEntities.remove(commentEntity);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", comments=" + commentEntities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity postEntity = (PostEntity) o;
        return  getId().equals(postEntity.getId()) &&
                getSubject().equals(postEntity.getSubject()) &&
                getContent().equals(postEntity.getContent()) &&
                getAuthor().equals(postEntity.getAuthor()) &&
                getCommentEntities().equals(postEntity.getCommentEntities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getContent(), getAuthor(), getCommentEntities());
    }
}
