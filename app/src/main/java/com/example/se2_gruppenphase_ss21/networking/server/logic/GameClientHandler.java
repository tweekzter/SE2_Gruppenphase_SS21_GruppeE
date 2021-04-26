package com.example.se2_gruppenphase_ss21.networking.server.logic;

import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;

import java.io.IOException;

public class GameClientHandler {

    private SocketWrapper client;
    private String nickname;
    private boolean isReady;

    public GameClientHandler(SocketWrapper client, String nickname) {
        this.client = client;
        this.nickname = nickname;
    }

    public void startGameLoop(GameRoom room) {
        new Thread(() -> {
            try {
                client.sendString("ok");
                room.broadcastUserList();
                room.broadcastIfGameStart();
                while(true) {
                    String fromUser = client.readString();
                    String[] params = fromUser.split("\\s");
                    switch (params[0]) {
                        case "ready":
                            isReady = Boolean.parseBoolean(params[1]);
                            room.broadcastReadyCount();
                            room.broadcastIfGameStart();
                            break;
                        default:
                            System.err.printf("Received invalid message from client %s%n", fromUser);
                    }
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

    public boolean isReady() {
        return isReady;
    }
}
