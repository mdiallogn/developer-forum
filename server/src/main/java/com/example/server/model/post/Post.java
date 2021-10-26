package com.example.server.model.post;

import com.example.server.model.comment.CommentEntity;
import com.example.server.model.user.UserEntity;

import java.util.List;

public interface Post {

    String getId();
    String getSubject();
    String getContent();
    UserEntity getAuthor();
    String getDate();

    void setSubject(String subject);
    void setContent(String content);
    void setAuthor(UserEntity author);
    void setCommentEntities(List<CommentEntity> commentEntities);
    List<CommentEntity> getCommentEntities();
    void addComment(CommentEntity commentEntity);
    void deleteComment(CommentEntity commentEntity);
}
