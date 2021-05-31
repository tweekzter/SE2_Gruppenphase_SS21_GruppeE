package com.example.se2_gruppenphase_ss21.networking.server;

import java.io.IOException;

public class ExternalServer {

    /**
     * Main for debugging and later for the external server.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 6789;

        System.out.printf("Starting External Server on port %d%n", port);
        GameServer server = new GameServer(port);
        server.setDynamicRoomCreation(true);
        server.start();
    }
}
