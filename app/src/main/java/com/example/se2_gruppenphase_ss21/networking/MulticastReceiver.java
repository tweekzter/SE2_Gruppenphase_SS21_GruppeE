package com.example.se2_gruppenphase_ss21.networking;

import com.example.se2_gruppenphase_ss21.networking.server.GameServer;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MulticastReceiver {
    private static final long TIMEOUT = 1000 * 10;

    private static CopyOnWriteArrayList<AvailableRoom> rooms = new CopyOnWriteArrayList<>();

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

    private static void startTimeoutCleaner() {
        new Thread(() -> {
            while (true) {
                for(AvailableRoom room : rooms) {
                    if(room.getReceivedAt() + TIMEOUT <= System.currentTimeMillis()) {
                        rooms.remove(room);
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static ArrayList<AvailableRoom> getRooms() {
        return new ArrayList<>(rooms);
    }
}
