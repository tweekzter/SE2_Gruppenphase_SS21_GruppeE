package com.example.se2_gruppenphase_ss21.networking;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class AvailableRoom {

    private int port, currentPlayers, maxPlayers;
    private String name;
    private InetAddress address;
    private long receivedAt;

    public AvailableRoom(String packet, InetAddress address) {
        String[] lines = packet.split("\n");
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
