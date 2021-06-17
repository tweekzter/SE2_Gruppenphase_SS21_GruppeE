package com.example.se2_gruppenphase_ss21;
public class Player {
    String position;
    String name;
    String points;

    public Player(){}

    public Player(String position, String name, String points) {
        this.position = position;
        this.name = name;
        this.points = points;
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

    @Override
    public String toString() {
        return "Player{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", points=" + points +
                '}';
    }
}
