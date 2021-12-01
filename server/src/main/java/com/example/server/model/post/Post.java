package com.example.server.model.post;

import com.example.server.model.comment.Comment;
import com.example.server.model.comment.CommentEntity;
import com.example.server.model.user.User;
import com.example.server.model.user.UserEntity;

import java.util.List;

public interface Post {

    String getId();
    String getSubject();
    String getContent();
    User getAuthor();
    String getDate();
    List<Comment> getComments();

    void setSubject(String subject);
    void setContent(String content);
    void setAuthor(User author);
    void setComments(List<Comment> comments);
    void addComment(Comment commentEntity);
    void deleteComment(Comment commentEntity);
    void setDate(String date);

    int getUpVote();
    void setUpVote(int value);
    int getDownVote();
    void setDownVote(int value);
    int increaseUpVote();
    int decreaseUpVote();
    int increaseDownVote();
    int decreaseDownVote();
}
