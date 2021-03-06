package com.example.se2_gruppenphase_ss21.networking.client.listeners;

public interface PreGameListener extends GeneralGameListener {
    /**
     * Information on how many users are ready in your room.
     * @param current
     * @param max
     */
    void readyCount(int current, int max);

    /**
     * Called once when the server starts the game.
     */
    void onGameStart();

    /**
     * Called when a list of the users currently in the room is transmitted.
     * @param nicknames
     */
    void receiveUserList(String[] nicknames);
}
