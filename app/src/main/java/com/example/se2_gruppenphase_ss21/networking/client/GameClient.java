package com.example.se2_gruppenphase_ss21.networking.client;

import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import java.io.IOException;
import java.net.Socket;

public class GameClient {

    private SocketWrapper socket;
    private String nickname;
    private String roomName;

    /**
     * Create a GameClient from a received room info and a nickname.
     * @param room the room to be joined
     * @param nickname the nickname to be used for joining
     * @throws IOException
     */
    public GameClient(AvailableRoom room, String nickname) throws IOException {
        this(room.getAddress().getHostName(), room.getPort(), room.getName(), nickname);
    }

    /**
     * Create a GameClient from the specified parameters.
     * @param hostname the hostname of the room
     * @param port the port of the room
     * @param roomName the rooms name
     * @param nickname the nickname to be used for joining
     * @throws IOException
     */
    public GameClient(String hostname, int port, String roomName, String nickname) throws IOException {
        try {
            Socket s = new Socket(hostname, port);
            this.socket = new SocketWrapper(s);
            this.nickname = nickname;
            this.roomName = roomName;
        }catch (IOException e) {
            throw e;
        }
    }

    /**
     * Try to connect to the room specified in the constructor.
     * Throws an GameLogicException if joining the room wasn't successful.
     * @throws IOException thrown if joining wasn't successful
     */
    public void connect() throws IOException, GameLogicException {
        try {
            socket.sendString(roomName);
            socket.sendString(nickname);

            String resp = socket.readString();
            if(!resp.equals("ok")) {
                throw new GameLogicException(resp);
            }
        }catch (IOException e) {
            throw e;
        }
    }
}
