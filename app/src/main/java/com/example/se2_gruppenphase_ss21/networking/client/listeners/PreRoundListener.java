package com.example.se2_gruppenphase_ss21.networking.client.listeners;

public interface PreRoundListener extends GeneralGameListener {
    /**
     * Called when a round begins specified user is requested to roll the dice.
     * @param nick
     */
    public void rollRequest(String nick);

    /**
     * Called when a dice roll result is received, approx. 5 seconds until beginPuzzle() is called.
     * @param result
     */
    public void rollResult(int result);
}
