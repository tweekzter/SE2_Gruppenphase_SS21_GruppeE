package com.example.se2_gruppenphase_ss21.networking.server;

import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.MulticastReceiver;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.ServerMessageListener;
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
        server.createRoom("TestRoom", 4);
        server.createRoom("SecondRoom", 8);
        server.setDynamicRoomCreation(false);
        server.start();

        ServerMessageListener listener = new ServerMessageListener() {
            @Override
            public void readyCount(int current, int max) {
                System.out.println("Users ready (" + current + "/" + max + ")");
            }

            @Override
            public void onGameStart() {
                System.out.println("Game Start");
            }

            @Override
            public void userDisconnect(String nickname) {
                System.out.println("User disconnected " + nickname);
            }

            @Override
            public void receiveUserList(String[] nicknames) {
                System.out.println("Connected users:");
                for(String nick : nicknames) {
                    System.out.println(nick);
                }
            }

            @Override
            public void unknownMessage(String message) {
                System.out.println("Received unknown message " + message);
            }
        };

        try {
            GameClient client = new GameClient("127.0.0.1", 6789, "TestRoom", "SomeUser");
            client.connect();
            GameClient client2 = new GameClient("127.0.0.1", 6789, "TestRoom", "AnotherUser");
            client2.connect();

            client.registerListener(listener);
            client2.registerListener(listener);

            client.startReceiveLoop();
            client2.startReceiveLoop();

            client.sendReady(true);
            client2.sendReady(true);
        }catch (GameLogicException e) {
            e.printStackTrace();
        }
        try {
            GameClient client = new GameClient("127.0.0.1", 6789, "NotARoom", "SomeUser");
            client.connect();
        }catch (GameLogicException e) {
            //Expected
            e.printStackTrace();
        }


        MulticastReceiver.startListen();
        while (true) {
            System.out.println("Multicast Rooms:");

            for(AvailableRoom room : MulticastReceiver.getRooms()) {
                //System.out.println(room + " - Age: " + room.getAge());
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
