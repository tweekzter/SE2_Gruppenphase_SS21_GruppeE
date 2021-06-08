package com.example.se2_gruppenphase_ss21.networking.server.logic;

import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;

import java.io.IOException;

public class GameClientHandler {

    private SocketWrapper client;
    private String nickname;
    private boolean isReady;
    private int rollResult;
    private long finishedPuzzleAt;
    private boolean bluff;

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
                        case "roll":
                            rollResult = Integer.parseInt(params[1]);
                            break;
                        case "finish_puzzle":
                            finishedPuzzleAt = System.currentTimeMillis();
                            bluff = Boolean.parseBoolean(params[1]);
                            break;
                        case "accuse":
                            GameClientHandler accused = room.getUserByNickname(params[1]);
                            if(accused.didBluff()) {

                            }else {

                            }
                            break;
                        default:
                            System.err.printf("Received invalid message from client %s%n", fromUser);
                    }
                }
            } catch (IOException e) {
                System.err.println(nickname + " disconnected");
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

    public void resetForNextRound() {
        rollResult = -1;
        finishedPuzzleAt = -1;
    }

    public boolean hasRolled() {
        return rollResult != -1;
    }

    public int getRollResult() {
        return rollResult;
    }

    public long getPuzzleFinishedAt() {
        return finishedPuzzleAt;
    }

    public boolean didBluff() {
        return bluff;
    }

    public void close() throws IOException {
        client.close();
    }
}
