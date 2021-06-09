package com.example.se2_gruppenphase_ss21.networking.client.listeners;

public interface PostRoundListener extends GeneralGameListener {
    /**
     * Transition to the dice activity
     */
    void transitionToDice();

    /**
     * Game is done, so exit
     */
    void endGame();
}
