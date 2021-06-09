package com.example.se2_gruppenphase_ss21.networking.client.listeners;

public interface GeneralGameListener {
    /**
     * Called when a user disconnects from the lobby.
     * @param nickname
     */
    void userDisconnect(String nickname);

    /**
     * Called when a message is received from the server that is not known.
     * @param message
     */
    void unknownMessage(String message);
}
