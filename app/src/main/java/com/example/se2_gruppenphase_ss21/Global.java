package com.example.se2_gruppenphase_ss21;

import com.example.se2_gruppenphase_ss21.networking.client.GameClient;

/**
 * Global class to store and pass data between Activities.
 * Not an elegant solution, but a way to bypass Androids annoying limitations. :)
 */
public class Global {
    public static GameClient client;
}
