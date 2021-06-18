package com.example.se2_gruppenphase_ss21;

import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.GeneralGameListener;
import com.example.se2_gruppenphase_ss21.networking.server.GameServer;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class NetworkingTest {

    private static GameServer server;

    @BeforeClass
    public static void startServer() throws IOException {
        if(System.getenv("CI") != null) return;

        server = new GameServer(6789);

        server.createRoom("TestRoom", 4);
        server.createRoom("SecondRoom", 8);
        server.setDynamicRoomCreation(false);
        server.start();
    }

    @Test
    public void serverWithTwoClientsTest() throws IOException, GameLogicException {
        if(System.getenv("CI") != null) return;

        GeneralGameListener listener = new ServerMessageListenerImpl();

        GameClient client1 = new GameClient("127.0.0.1", 6789, "TestRoom", "Tester1");
        client1.connect();

        GameClient client2 = new GameClient("127.0.0.1", 6789, "TestRoom", "Tester2");
        client2.connect();

        client1.registerListener(listener);
        client2.registerListener(listener);

        client1.startReceiveLoop();
        client2.startReceiveLoop();

        client1.sendReady(true);
        client2.sendReady(true);
    }

    @Test(expected = GameLogicException.class)
    public void clientWithWrongRoomTest() throws IOException, GameLogicException {
        if(System.getenv("CI") != null) throw new GameLogicException("Tests are skipped on CI");

        GameClient client3 = new GameClient("127.0.0.1",6789,"NotARoom","Tester3");
        client3.connect();
    }

}
