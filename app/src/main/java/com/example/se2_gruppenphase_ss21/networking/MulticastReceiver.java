package com.example.se2_gruppenphase_ss21.networking;

import com.example.se2_gruppenphase_ss21.networking.server.GameServer;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MulticastReceiver {
    private static final int TIMEOUT = 1000 * 10;

    private static CopyOnWriteArrayList<AvailableRoom> rooms = new CopyOnWriteArrayList<>();

    private MulticastReceiver(){}

    /**
     * Start listening for available games on the local network.
     * Starts a new thread, so it can easily be called from Main-Thread.
     */
    public static void startListen() {
        new Thread(() -> {
            try {
                MulticastSocket socket = new MulticastSocket(GameServer.BROADCAST_PORT);
                InetAddress group = InetAddress.getByName(GameServer.MULTICAST_ADDRESS);
                socket.joinGroup(group);
                byte[] buffer = new byte[1024];

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    AvailableRoom newRoom = new AvailableRoom(new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8), packet.getAddress());
                    for(AvailableRoom room : rooms) {
                        if(newRoom.equals(room)) {
                            rooms.remove(room);
                        }
                    }
                    rooms.add(newRoom);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        startTimeoutCleaner();
    }

    /**
     * Cleanup received Rooms after they are older than the timeout.
     */
    private static void startTimeoutCleaner() {
        new Thread(() -> {
            while (true) {
                for(AvailableRoom room : rooms) {
                    if(room.getAge() > TIMEOUT) {
                        rooms.remove(room);
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    /**
     * Receive a list of available rooms on the network.
     * @return the list of rooms
     */
    public static List<AvailableRoom> getRooms() {
        return new ArrayList<>(rooms);
    }
}
