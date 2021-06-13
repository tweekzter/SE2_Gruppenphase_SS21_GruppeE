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

    /**
     * Called in return when someone was accused of cheating and returns, if the person in question cheated. <br>
     * If wasCheating, accused loses amount of points passed as pointLoss. <br>
     * If !wasCheating, accuser loses 1 point.
     * @param accuser
     * @param accused
     * @param wasCheating
     * @param pointLoss
     */
    void accusationResult(String accuser, String accused, boolean wasCheating, int pointLoss);
}
