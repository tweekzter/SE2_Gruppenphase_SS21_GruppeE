package com.example.se2_gruppenphase_ss21.networking.server.logic;

import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;

import java.io.IOException;

public class GameClientHandler {

    private SocketWrapper client;

    private String nickname;

    public GameClientHandler(SocketWrapper client, String nickname) {
        this.client = client;
        this.nickname = nickname;
    }

    public void startGameLoop(GameRoom room) {
        new Thread(() -> {
            try {
                client.sendString("ok");

                while(true) {
                    String fromUser = client.readString();

                    System.out.println("Message from user: " + fromUser);
                }
            } catch (IOException e) {
                e.printStackTrace();
                room.removeUser(this);
            }
        }).start();
    }

    public void sendMessage(String msg) throws IOException {
        client.sendString(msg);
    }

    public String getNickname() {
        return nickname;
    }
}
