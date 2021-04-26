package com.example.se2_gruppenphase_ss21.networking;

import java.net.InetAddress;

public class AvailableRoom {

    private int port;
    private int currentPlayers;
    private int maxPlayers;
    private String name;
    private InetAddress address;
    private long receivedAt;

    /**
     * Creates a Available-Room from a given 4 line string that contains port, name, currentPlayers, maxPlayers
     * and the given address. Also saves the current time as the received time.
     * @param packet 4 line string with information about the room
     * @param address address of the room
     */
    public AvailableRoom(String packet, InetAddress address) {
        String[] lines = packet.split("\n");

        if(lines.length < 4) {
            throw new IllegalArgumentException("Cannot create room from " + lines.length + " line string");
        }

        this.port = Integer.parseInt(lines[0]);
        this.name = lines[1];
        this.currentPlayers = Integer.parseInt(lines[2]);
        this.maxPlayers = Integer.parseInt(lines[3]);

        this.address = address;
        this.receivedAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("%s:%d %s (%d/%d)", address, port, name, currentPlayers, maxPlayers);
    }

    @Override
    public boolean equals(Object o) {
        AvailableRoom room = (AvailableRoom) o;
        return getPort() == room.getPort() && getAddress().equals(room.getAddress()) && getName().equals(room.getName());
    }

    public int getPort() {
        return port;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getName() {
        return name;
    }

    public InetAddress getAddress() {
        return address;
    }

    public long getReceivedAt() {
        return receivedAt;
    }
}
