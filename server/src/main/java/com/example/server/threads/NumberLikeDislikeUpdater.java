package com.example.server.threads;

import com.example.server.model.post.Post;
import com.example.server.services.Vote;

public class NumberLikeDislikeUpdater implements Runnable {

    private final Vote vote;
    private final int operation;
    private final Post post;

    public NumberLikeDislikeUpdater(Vote vote, int operation, Post post) {
        this.vote = vote;
        this.operation = operation;
        this.post = post;
    }

    @Override
    public void run() {
        vote.upDownNumberLikeDislike(operation);
        post.setTotalLikeDislike(vote.getNumberLikeDislike());
    }
}
