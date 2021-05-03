package com.example.se2_gruppenphase_ss21;
public class Player {
    int position;
    String name;
    String points;

    public Player(int position, String name, String points) {
        this.position = position;
        this.name = name;
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
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
