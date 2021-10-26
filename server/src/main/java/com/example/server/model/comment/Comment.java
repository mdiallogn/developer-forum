package com.example.server.model.comment;

import com.example.server.model.user.UserEntity;

import java.util.List;

public interface Comment {

    String getId();
    String getMessage();
    UserEntity getAuthor();
    String getDate();

    void setDate(String date);
    List<CommentEntity> getReply();
    void setMessage(String message);
    void setAuthor(UserEntity author);
    void addReply(CommentEntity commentEntity);
}
