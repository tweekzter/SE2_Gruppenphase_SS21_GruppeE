package com.example.se2_gruppenphase_ss21.networking.server;

import java.io.IOException;

public class ExternalServer {

    /**
     * Main for debugging and later for the external server.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(6789);
        server.start();
    }
}
