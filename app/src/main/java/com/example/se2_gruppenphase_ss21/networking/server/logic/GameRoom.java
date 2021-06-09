package com.example.se2_gruppenphase_ss21.networking.server.logic;

import com.example.se2_gruppenphase_ss21.networking.ServerMessage;
import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;
import com.example.se2_gruppenphase_ss21.networking.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameRoom {
    private static final int DEFAULT_MAX_USERS = 4;
    private static final int ROUND_COUNT = 9;

    private ArrayList<GameClientHandler> handlers = new ArrayList<>();

    private int maxUsers;

    public GameRoomState state;

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
        } else if (nickname.contains(",") || nickname.contains(":") || nickname.contains("&") || nickname.contains(";")) {
            throw new GameLogicException("Nickname contains forbidden characters \",:&;\"");
        } else {
            GameClientHandler handler = new GameClientHandler(client, nickname);
            handler.startGameLoop(this);
            handlers.add(handler);
        }
    }

    public void broadcastMessage(ServerMessage type, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());
        for(Object arg : args) {
            sb.append(",");
            sb.append(arg);
        }

        for(GameClientHandler handler : handlers) {
            try {
                handler.sendMessage(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
                removeUser(handler);
            }
        }
    }

    public void removeUser(GameClientHandler handler) {
        handlers.remove(handler);
        broadcastMessage(ServerMessage.DISCONNECT, handler.getNickname());
    }

    public void broadcastUserList() {
        broadcastMessage(ServerMessage.USER_LIST, listNicknames());
    }

    public void broadcastReadyCount() {
        broadcastMessage(ServerMessage.READY, usersReady(), handlers.size());
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
            broadcastMessage(ServerMessage.GAME_START);
            startGameLoop();
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

    public GameClientHandler getUserByNickname(String nickname) {
        for(GameClientHandler handler : handlers) {
            if(handler.getNickname().equals(nickname))
                return handler;
        }

        return null;
    }

    public int userCount() {
        return handlers.size();
    }

    public int maxUsers() {
        return maxUsers;
    }

    public void startGameLoop() {
        new Thread(() -> {
            int round = 0;
            while (true) {
                if(round >= ROUND_COUNT) {
                    break;
                }else if(handlers.size() == 0) {
                    System.err.println("Room was empty when starting round, closing room!");
                    break;
                }

                for(GameClientHandler handler : handlers)
                    handler.resetForNextRound();

                handleDiceRoll(round);

                handlePuzzle();

                sendPlacements();

                Util.sleep(10, 0);

                if(++round == ROUND_COUNT) {
                    broadcastMessage(ServerMessage.END_GAME);
                }else {
                    broadcastMessage(ServerMessage.END_SCOREBOARD);
                }

            }
            resetRoom();

            //Round end
        }).start();
    }

    private void sendPlacements() {
        Map<String, Integer> placements = calculatePlacements();
        String[] foo = new String[placements.keySet().size()];

        int c = 0;
        for(String nick : placements.keySet()) {
            foo[c] = nick + ":" + placements.get(nick);
            c++;
        }

        broadcastMessage(ServerMessage.PLACEMENTS, foo);
    }

    private void handlePuzzle() {
        long finishUntil = System.currentTimeMillis() + (60 * 1000);
        broadcastMessage(ServerMessage.BEGIN_PUZZLE, finishUntil);
        Util.sleep(0, finishUntil - System.currentTimeMillis());
    }

    private void handleDiceRoll(int round) {
        broadcastMessage(ServerMessage.PLAY_DICE_ANIMATION, (new Random().nextInt(6 - 1) + 1));

        Util.sleep(8, 0);

        broadcastMessage(ServerMessage.END_DICE_ANIMATION);
    }

    private void resetRoom() {
        state = GameRoomState.RESTARTING;
        for(GameClientHandler handler : handlers)
            handler.close();
        handlers.clear();
        System.out.println("Room reset done!");
        state = GameRoomState.WAITING;
    }

    private Map<String, Integer> calculatePlacements() {
        Map<String, Long> tempMap = new HashMap<>();
        Map<String, Integer> placementMap = new HashMap<>();

        for(GameClientHandler handler : handlers) {
            if(handler.getPuzzleFinishedAt() != -1) {
                tempMap.put(handler.getNickname(), handler.getPuzzleFinishedAt());
            }
        }

        for(GameClientHandler handler : handlers) {
            if(handler.getPuzzleFinishedAt() == -1) {
                placementMap.put(handler.getNickname(), tempMap.keySet().size() + 1);
            }
        }

        int c = 1;
        while (!tempMap.keySet().isEmpty()) {
            String smallest = keyFromPairWithSmallestVal(tempMap);
            tempMap.remove(smallest);
            placementMap.put(smallest, c);
            c++;
        }

        return placementMap;
    }

    private String keyFromPairWithSmallestVal(Map<String, Long> map) {
        long smallest = Integer.MAX_VALUE;
        String val = "";

        if(map.keySet().isEmpty()) {
            throw new RuntimeException("map is empty");
        }

        for(String key : map.keySet()) {
            if(map.get(key) < smallest) {
                smallest = map.get(key);
                val = key;
            }
        }

        return val;
    }
}
