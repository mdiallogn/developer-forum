package com.example.server.concurrency;

import java.util.ArrayList;
import java.util.List;

/**
 * Put a locker on a specific post
 */
public class PostLocker {
    /**
     * Contains the ID of all post currently on update
     */
    private volatile List<String> posts = new ArrayList<>();

    /**
     * Verify if a post is already locked.
     * @param id Post ID
     * @return
     */
    public boolean isAlreadyLocked(String id) {
        return !posts.stream().filter(post -> post.equals(id)).findFirst().isEmpty();
    }

    /**
     * Put a locker on the specific post.
     * @param id
     */
    public void lock(String id) {
        if (!isAlreadyLocked(id)) {
            posts.add(id);
        }
    }

    /**
     * Remove locker from a specific post.
     * @param id
     */
    public void unlock(String id) {
        posts.removeIf(p -> p.equals(id));
    }
}
