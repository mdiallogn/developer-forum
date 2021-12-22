package com.example.server.services;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Vote {

    private int numberLikeDislike;
    private boolean update = true;
    Logger logger = Logger.getLogger(Vote.class.getName());

    public Vote(int numberLikeDislike) {
        this.numberLikeDislike = numberLikeDislike;
    }

    public synchronized void upDownNumberLikeDislike(int operation) {
        //opration==1 --> up nbre like/dislike
        //otherwise --> down nbre like/dislike
        while (!update) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Thread interrupted", e);
            }
        }
        update = true;
        if (operation == 1) increment();
        else decrement();
        notify();
    }

    public void increment() {
        this.numberLikeDislike++;
    }

    public void decrement() {
        this.numberLikeDislike--;
    }

    public int getNumberLikeDislike() {
        return numberLikeDislike;
    }
}
