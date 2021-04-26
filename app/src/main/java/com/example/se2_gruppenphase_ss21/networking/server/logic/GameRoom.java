package com.example.se2_gruppenphase_ss21.networking.server.logic;

import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;

public class GameRoom {
    private static final int DEFAULT_MAX_USERS = 4;

    private ArrayList<GameClientHandler> handlers = new ArrayList<>();

    private int maxUsers;

    private GameRoomState state;

    public GameRoom() {
        this(DEFAULT_MAX_USERS);
    }
    public GameRoom(int maxUsers) {
        if(maxUsers < 2) {
            throw new IllegalArgumentException("A GameRoom must at least be for 2 users");
        }

        this.maxUsers = maxUsers;
        this.state = GameRoomState.WAITING;
    }

    public void addUser(SocketWrapper client, String nickname) throws GameLogicException {
        if (state != GameRoomState.WAITING) {
            throw new GameLogicException("This room is currently playing or restarting");
        } else if (handlers.size() >= maxUsers) {
            throw new GameLogicException("Room already full");
        } else if (hasNickname(nickname)) {
            throw new GameLogicException("Nickname taken");
        } else {
            GameClientHandler handler = new GameClientHandler(client, nickname);
            handler.startGameLoop(this);
            handlers.add(handler);
        }
    }

    public void broadcastMessage(String msg) {
        for(GameClientHandler handler : handlers) {
            try {
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
                removeUser(handler);
            }
        }
    }

    public void removeUser(GameClientHandler handler) {
        handlers.remove(handler);
        broadcastMessage("disconnect " + handler.getNickname());
    }

    public String[] listNicknames() {
        String[] users = new String[handlers.size()];
        for(int i = 0; i < users.length; i++) {
            users[i] = handlers.get(i).getNickname();
        }

        return users;
    }

    public boolean hasNickname(String nickname) {
        for(String name : listNicknames()) {
            if(name.equals(nickname)) {
                return true;
            }
        }

        return false;
    }

    public int userCount() {
        return handlers.size();
    }

    public int maxUsers() {
        return maxUsers;
    }
}
