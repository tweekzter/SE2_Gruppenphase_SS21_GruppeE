package com.example.se2_gruppenphase_ss21.networking.client;

/**
 * This is an interface for Listener that can be registered to a GameClient.
 * All methods in the implementation shouldn't take too long since they are called on the same thread as the reading from server.
 * So any new message from the server is blocked until the previous function is done.
 */
public interface ServerMessageListener {
    /**
     * Information on how many users are ready in your room.
     * @param current
     * @param max
     */
    public void readyCount(int current, int max);

    /**
     * Called when the server starts the game.
     */
    public void onGameStart();

    /**
     * Called when a user disconnects from the lobby.
     * @param nickname
     */
    public void userDisconnect(String nickname);

    /**
     * Called when a list of the users currently in the room is transmitted.
     * @param nicknames
     */
    public void receiveUserList(String[] nicknames);

    /**
     * Called when a specified user is requested to roll the dice
     * @param nick
     */
    public void rollRequest(String nick);

    /**
     * Called when a dice roll result is received
     * @param result
     */
    public void rollResult(int result);

    /**
     * Called when a message is received from the server that is not known.
     * @param message
     */
    public void unknownMessage(String message);
}
