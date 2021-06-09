package com.example.se2_gruppenphase_ss21.networking.server.logic;

import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;
import com.example.se2_gruppenphase_ss21.networking.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameRoom {
    private static final int DEFAULT_MAX_USERS = 4;
    private static final int ROUND_COUNT = 9;

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
        } else if (nickname.contains(",") || nickname.contains(":") || nickname.contains("&") || nickname.contains(";")) {
            throw new GameLogicException("Nickname contains forbidden characters \",:&;\"");
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
                    System.err.println("Room was empty when trying to start round, closing room!");
                    break;
                }

                for(GameClientHandler handler : handlers)
                    handler.resetForNextRound();

                handleDiceRoll(round);

                handlePuzzle();

                sendPlacements();

                round++;
            }

            resetRoom();

            //Round end
        }).start();
    }

    private void sendPlacements() {
        Map<String, Integer> placements = calculatePlacements();
        StringBuilder sb = new StringBuilder("placements ");
        for(String nick : placements.keySet()) {
            sb.append(nick).append(":").append(placements.get(nick)).append(";");
        }
        broadcastMessage(sb.substring(0, sb.length() - 1));
        Util.sleep(10, 0);
    }

    private void handlePuzzle() {
        long finishUntil = System.currentTimeMillis() + (60 * 1000);
        broadcastMessage("begin_puzzle " + finishUntil);
        Util.sleep(0, finishUntil - System.currentTimeMillis());
    }

    private void handleDiceRoll(int round) {
        /*
        ***** FOLLOWING FEATURE IS CURRENTLY NOT NEEDED !! *****


        GameClientHandler diceRoller = handlers.get(round % handlers.size());
        broadcastMessage("roll_request " + diceRoller.getNickname());
        int rollResult;
        //Wait for user to roll or disconnect
        while (true) {
            if(!handlers.contains(diceRoller)) {
                rollResult = (int) (Math.random() * 6 + 1);
                break;
            }else if(diceRoller.hasRolled()) {
                rollResult = diceRoller.getRollResult();
                break;
            }

            Util.sleep(0, 200);
        }
        broadcastMessage("roll_result " + rollResult);
         */
        Util.sleep(8, 0);
    }

    private void resetRoom() {
        state = GameRoomState.RESTARTING;
        for(GameClientHandler handler : handlers) {
            try {
                handler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
