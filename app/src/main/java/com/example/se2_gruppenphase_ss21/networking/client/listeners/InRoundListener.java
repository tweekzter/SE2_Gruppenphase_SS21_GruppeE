package com.example.se2_gruppenphase_ss21.networking.client.listeners;

import java.util.Map;

public interface InRoundListener extends GeneralGameListener {
    /**
     * Called when the puzzle should be presented.
     * @param finishUntil the time until the puzzle should be finished
     */
    void beginPuzzle(long finishUntil);

    /**
     * Is called when the placements are received after the Puzzle is finished, marks the end of a round.
     * Next roll request is received in approx. 10 seconds.
     * @param placements
     */
    void placementsReceived(Map<String, Integer> placements);
}
