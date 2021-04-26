package com.example.se2_gruppenphase_ss21.networking.server;

import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.MulticastReceiver;

import java.io.IOException;

public class ExternalServer {

    /**
     * Main for debugging and later for the external server.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
        server.createRoom("TestRoom", 8);
        server.createRoom("SecondRoom", 4);
        server.start();

        MulticastReceiver.startListen();
        while (true) {
            System.out.println("Multicast Rooms:");

            for(AvailableRoom room : MulticastReceiver.getRooms()) {
                System.out.println(room + " - Age: " + (System.currentTimeMillis() - room.getReceivedAt()));
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
