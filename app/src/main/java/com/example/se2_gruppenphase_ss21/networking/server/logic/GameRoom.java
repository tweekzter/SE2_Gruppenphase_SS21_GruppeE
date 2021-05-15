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
        broadcastMessage("disconnect_user " + handler.getNickname());
    }

    public void broadcastUserList() {
        String[] nicks = listNicknames();
        StringBuilder sb = new StringBuilder("user_list ");
        for(int i = 0; i < nicks.length; i++) {
            sb.append(nicks[i]);
            if (i != nicks.length - 1) {
                sb.append(",");
            }
        }

        broadcastMessage(sb.toString());
    }

    public void broadcastReadyCount() {
        if(state == GameRoomState.WAITING) {
            broadcastMessage("users_ready " + usersReady() + "," + handlers.size());
        }
    }

    public int usersReady() {
        int count = 0;
        for(GameClientHandler handler : handlers) {
            count += handler.isReady() ? 1 : 0;
        }
        return count;
    }

    public void broadcastIfGameStart() {
        if((handlers.size() == maxUsers || usersReady() == handlers.size()) && state == GameRoomState.WAITING) {
            state = GameRoomState.PLAYING;
            broadcastMessage("game_start");
        }
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