package com.example.se2_gruppenphase_ss21.networking.server;

import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.MulticastReceiver;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import java.io.IOException;

public class ExternalServer {

    /**
     * Main for debugging and later for the external server.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(6789);
        server.createRoom("TestRoom", 8);
        server.createRoom("SecondRoom", 4);
        server.setDynamicRoomCreation(false);
        server.start();

        try {
            GameClient client = new GameClient("127.0.0.1", 6789, "TestRoom", "SomeUser");
            client.connect();
        }catch (GameLogicException e) {
            e.printStackTrace();
        }
        try {
            GameClient client2 = new GameClient("127.0.0.1", 6789, "NotARoom", "SomeUser");
            client2.connect();
        }catch (GameLogicException e) {
            e.printStackTrace();
        }

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
