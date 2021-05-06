package com.example.se2_gruppenphase_ss21.networking.client;

public class ServerMessageListenerImpl implements ServerMessageListener {
    @Override
    public void readyCount(int current, int max) {
        System.out.println("Users ready (" + current + "/" + max + ")");
    }

    @Override
    public void onGameStart() {
        System.out.println("Game Start");
    }

    @Override
    public void userDisconnect(String nickname) {
        System.out.println("User disconnected " + nickname);
    }

    @Override
    public void receiveUserList(String[] nicknames) {
        System.out.println("Connected users:");
        for(String nick : nicknames) {
            System.out.println(nick);
        }
    }

    @Override
    public void unknownMessage(String message) {
        System.out.println("Received unknown message " + message);
    }
}
