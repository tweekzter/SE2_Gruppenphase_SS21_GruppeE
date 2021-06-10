package com.example.se2_gruppenphase_ss21.networking.client;

public class PlayerPlacement {
    public String nickname;
    public int placement, points;
    public long timeTaken;
    public boolean didFinnish;

    public PlayerPlacement(String serialized) {
        String[] split = serialized.split(":");
        nickname = split[0];
        placement = Integer.parseInt(split[1]);
        points = Integer.parseInt(split[2]);
        didFinnish = Boolean.parseBoolean(split[3]);
        timeTaken = Long.parseLong(split[4]);
    }

    public String getNickname() {
        return nickname;
    }

    public int getPlacement() {
        return placement;
    }

    public int getPoints() {
        return points;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public boolean isDidFinnish() {
        return didFinnish;
    }
}
