package com.example.se2_gruppenphase_ss21;
public class Player {
    String position;
    String name;
    String points;
    boolean canChallenge;

    public Player(){}

    public Player(String position, String name, String points) {
        this.position = position;
        this.name = name;
        this.points = points;
        this.canChallenge = true;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public boolean isCanChallenge() {
        return canChallenge;
    }

    public void setCanChallenge(boolean canChallenge) {
        this.canChallenge = canChallenge;
    }

    @Override
    public String toString() {
        return "Player{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", points=" + points +
                '}';
    }
}
