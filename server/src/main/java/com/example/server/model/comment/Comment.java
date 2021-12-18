package com.example.server.model.comment;

import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;

import java.util.List;

public interface Comment {

    String getId();
    String getMessage();
    UserEntity getAuthor();
    String getDate();
    List<Comment> getReply();

    void setDate(String date);
    void setMessage(String message);
    void setAuthor(UserEntity author);
    void setReply(List<Comment> comment);
    void addReply(Comment comment);
    void deleteReply(Comment comment);
}
