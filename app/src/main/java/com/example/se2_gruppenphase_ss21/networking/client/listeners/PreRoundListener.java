package com.example.se2_gruppenphase_ss21.networking.client.listeners;

public interface PreRoundListener extends GeneralGameListener {
    /**
     * Called to start playing a dice animation with a determined result
     * @param result the result the animation should show
     */
    void playDiceAnimation(int result);

    /**
     * Called when the transition to the puzzle Activity should be made
     */
    void transitionToPuzzle();
}
