package com.example.se2_gruppenphase_ss21.networking.client;

import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;
import com.example.se2_gruppenphase_ss21.networking.server.GameServer;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import java.io.IOException;
import java.net.Socket;

public class GameClient {

    private SocketWrapper socket;
    private String nickname;
    private String roomName;
    private boolean isConnected;

    private ServerMessageListener listener;

    public GameClient(GameServer server, String roomName, String nickname) throws IOException {
        this("127.0.0.1", server.getPort(), roomName, nickname);
    }

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
        Socket s = new Socket(hostname, port);
        this.socket = new SocketWrapper(s);
        this.nickname = nickname;
        this.roomName = roomName;
    }

    /**
     * Try to connect to the room specified in the constructor.
     * Throws an GameLogicException if joining the room wasn't successful.
     * @throws IOException thrown if joining wasn't successful
     */
    public void connect() throws IOException, GameLogicException {
        socket.sendString(roomName);
        socket.sendString(nickname);

        String resp = socket.readString();
        if (!resp.equals("ok")) {
            throw new GameLogicException(resp);
        }
        isConnected = true;
    }

    /**
     * Starts the thread to receive messages from the server.
     * Needs a Listener to be set via registerListener() first.
     */
    public void startReceiveLoop() {
        if (listener == null) {
            throw new RuntimeException("No listener registered");
        }else if(!isConnected) {
            throw new RuntimeException("The client is not connected to any room");
        }

        new Thread(() -> {
            while (true) {
                try {
                    String fromServer = socket.readString();
                    String[] params = fromServer.split("\\s");
                    switch (params[0]) {
                        case "game_start":
                            listener.onGameStart();
                            break;
                        case "users_ready":
                            String[] split = params[1].split(",");
                            int current = Integer.parseInt(split[0]);
                            int max = Integer.parseInt(split[1]);
                            listener.readyCount(current, max);
                            break;
                        case "user_list":
                            String[] nicknames = params[1].split(",");
                            listener.receiveUserList(nicknames);
                            break;
                        case "disconnect_user":
                            String name = params[1];
                            listener.userDisconnect(name);
                            break;
                        case "roll_request":
                            String nick = params[1];
                            listener.rollRequest(nick);
                            break;
                        case "roll_result":
                            int result = Integer.parseInt(params[1]);
                            listener.rollResult(result);
                            break;
                        default:
                            listener.unknownMessage(fromServer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Tells the server whether this client is ready or not.
     * @param isReady
     * @throws IOException
     */
    public void sendReady(boolean isReady) throws IOException {
        if (!isConnected) {
            throw new RuntimeException("Client is not connected");
        }

        socket.sendString("ready " + isReady);
    }

    public void sendRollResult(int result) throws IOException {
        if (!isConnected) {
            throw new RuntimeException("Client is not connected");
        }

        socket.sendString("roll " + result);
    }

    public void sendPuzzleOver(int millisRemaining, boolean didFinish) throws IOException {
        if (!isConnected) {
            throw new RuntimeException("Client is not connected");
        }

        socket.sendString("finish_puzzle " + (didFinish ? millisRemaining : -1));
    }

    /**
     * Sets a Listener to listen for messages from the server.
     * Only one listener can be registered at a time so this overwrites any previously registered Listener.
     * @param listener the Listener to be registered
     */
    public void registerListener(ServerMessageListener listener) {
        this.listener = listener;
    }
}
