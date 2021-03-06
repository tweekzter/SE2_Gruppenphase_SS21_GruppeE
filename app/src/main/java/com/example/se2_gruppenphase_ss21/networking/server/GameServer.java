package com.example.se2_gruppenphase_ss21.networking.server;

import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;
import com.example.se2_gruppenphase_ss21.networking.Util;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameRoom;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class GameServer extends Thread {
    public static final int BROADCAST_PORT = 7610;
    public static final String MULTICAST_ADDRESS = "230.0.0.0";

    private ServerSocket server;

    private boolean doBroadcast;

    private HashMap<String, GameRoom> rooms = new HashMap<>();

    private boolean dynamicRoomCreation = false;

    /**
     * Creates a Server with a randomly assigned port and broadcasting enabled.
     * @throws IOException
     */
    public GameServer() throws IOException {
        this(0);
    }

    /**
     * Creates a Server with the assigned port and broadcasting enabled.
     * @param port the port for the Server to listen
     * @throws IOException
     */
    public GameServer(int port) throws IOException {
        this(port, true);
    }

    /**
     * Creates a Server with the assigned port and broadcasting if enabled.
     * @param port the port for the Server to listen
     * @param doBroadcast whether to broadcast rooms on local network
     * @throws IOException
     */
    public GameServer(int port, boolean doBroadcast) throws IOException {
        server = new ServerSocket(port);
        this.doBroadcast = doBroadcast;
    }

    @Override
    public void run() {
        if(doBroadcast) {
            startRoomBroadcast();
        }

        while (true) {
            SocketWrapper client = null;
            try {
                client = new SocketWrapper(server.accept());

                String roomName = client.readString();
                String nickname = client.readString();

                if (rooms.containsKey(roomName)) {
                    rooms.get(roomName).addUser(client, nickname);
                } else if(dynamicRoomCreation) {
                    GameRoom room = new GameRoom();
                    room.addUser(client, nickname);
                    rooms.put(roomName, room);
                }else {
                    throw new GameLogicException("No such room");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (GameLogicException gle) {
                try {
                    if(client != null) {
                        client.sendString(gle.getMessage());
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * If true allows clients to create a room if it doesn't exists.
     * @param v
     */
    public void setDynamicRoomCreation(boolean v) {
        dynamicRoomCreation = v;
    }

    /**
     * Create a room with the given params and add it to the servers list of rooms.
     * @param name the name for the room
     * @param maxUser the max amount of users for the room >2
     */
    public void createRoom(String name, int maxUser) {
        GameRoom room = new GameRoom(maxUser);
        System.out.printf("Created Room %s with capacity of %d\n", name, maxUser);
        rooms.put(name, room);
    }

    private void startRoomBroadcast() {
        new Thread(() -> {
            while (true) {
                try {
                    DatagramSocket datagramSocket = new DatagramSocket();

                    for(String roomName : rooms.keySet()) {
                        String msg = String.format("%d\n%s\n%d\n%d",
                                server.getLocalPort(),
                                roomName,
                                rooms.get(roomName).userCount(),
                                rooms.get(roomName).maxUsers());

                        byte[] buffer = msg.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MULTICAST_ADDRESS), BROADCAST_PORT);
                        datagramSocket.send(packet);
                    }

                    datagramSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Util.sleep(2, 0);
            }
        }).start();
    }

    public int getPort() {
        return server.getLocalPort();
    }
}
