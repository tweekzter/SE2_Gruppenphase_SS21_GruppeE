package com.example.se2_gruppenphase_ss21.networking.server.logic;

import com.example.se2_gruppenphase_ss21.networking.ServerMessage;
import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;

import java.io.IOException;

public class GameClientHandler implements Comparable {

    private SocketWrapper client;
    private String nickname;
    private boolean isReady;
    private long finishedPuzzleAt;
    private boolean bluff;
    private int points, lastPointIncrease;

    public GameClientHandler(SocketWrapper client, String nickname) {
        this.client = client;
        this.nickname = nickname;
    }

    public void startGameLoop(GameRoom room) {
        new Thread(() -> {
            try {
                if(room.state == GameRoomState.WAITING) {
                    client.sendString("ok");
                }else {
                    client.sendString("Room not accepting new players!");
                    close();
                    return;
                }

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
                        case "finish_puzzle":
                            finishedPuzzleAt = System.currentTimeMillis();
                            bluff = Boolean.parseBoolean(params[1]);
                            break;
                        case "accuse":
                            GameClientHandler accused = room.getUserByNickname(params[1]);
                            if(!accused.didBluff())
                                points--;
                            room.broadcastMessage(ServerMessage.ACCUSATION_RESULT, getNickname(), accused.getNickname(), accused.didBluff(), accused.didBluff() ? accused.undoLastPointGain() : 0);
                            break;
                        case "disconnect":
                            room.removeUser(this);
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
        finishedPuzzleAt = Long.MAX_VALUE;
        bluff = false;
    }

    public boolean didFinnishPuzzle() {
        return finishedPuzzleAt == Long.MAX_VALUE;
    }

    public long getPuzzleFinishedAt() {
        return finishedPuzzleAt;
    }

    public boolean didBluff() {
        return bluff;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amount) {
        lastPointIncrease = amount;
        points += amount;
    }

    public int undoLastPointGain() {
        points -= lastPointIncrease;
        return lastPointIncrease;
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(Object o) {
        GameClientHandler other = (GameClientHandler) o;
        return Integer.compare(getPoints(), other.getPoints());
    }
}
