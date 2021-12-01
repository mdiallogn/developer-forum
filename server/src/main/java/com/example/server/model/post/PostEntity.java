package com.example.server.model.post;

import com.example.server.model.comment.Comment;
import com.example.server.model.user.User;
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
@Document(collection = "post")
public class PostEntity implements Post{

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
    @Field("upVote")
    private int upVote;
    @Field("downVote")
    private int downVote;

    public PostEntity(String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.author = null;
        this.date = Timestamp.valueOf(LocalDateTime.now()).toString();
        this.comments = new ArrayList<>();
        upVote = 0;
        downVote = 0;
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
    public User getAuthor() {
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
    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    @Override
    public void deleteComment(Comment comment){
        this.comments.remove(comment);
    }

    @Override
    public void setDate(String date){
        this.date = date;
    }

    @Override
    public void setUpVote(int value) {
        upVote = value;
    }

    @Override
    public void setDownVote(int value) {
        downVote = value;
    }

    @Override
    public int increaseUpVote() {
        return ++upVote;
    }

    @Override
    public int decreaseUpVote() {
        return --upVote;
    }

    @Override
    public int increaseDownVote() {
        return ++downVote;
    }

    @Override
    public int decreaseDownVote() {
        return --downVote;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author +
                ", comments=" + comments +
                ", upVote=" + upVote +
                "downVote=" + downVote +
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
                getComments().equals(postEntity.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getContent(), getAuthor(), getComments());
    }
}
