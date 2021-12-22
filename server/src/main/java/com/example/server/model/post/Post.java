package com.example.server.model.post;

import com.example.server.model.comment.Comment;
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
    void addUpVoter(UserEntity voter);
    void removeUpVoter(UserEntity voter);
    void addDownVoter(UserEntity voter);
    void removeDownVoter(UserEntity voter);
    List<UserEntity> getUpVoters();
    List<UserEntity> getDownVoters();
    int getTotalLikeDislike();
    void setTotalLikeDislike(int totalLikeDislike);
}
