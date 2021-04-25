package com.example.se2_gruppenphase_ss21.networking.server;

import java.io.IOException;

public class ExternalServer {
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
        server.createRoom("TestRoom", 8);
        server.start();
    }
}
