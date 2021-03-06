package com.example.se2_gruppenphase_ss21.networking.client;

import android.util.Log;

import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.ClientMessage;
import com.example.se2_gruppenphase_ss21.networking.ServerMessage;
import com.example.se2_gruppenphase_ss21.networking.SocketWrapper;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.*;
import com.example.se2_gruppenphase_ss21.networking.server.GameServer;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GameClient {

    private SocketWrapper socket;
    private String nickname;
    private String roomName;
    private boolean isConnected;

    private volatile GeneralGameListener listener;

    private static GameClient activeGameClient;


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
        if(!isConnected) {
            throw new RuntimeException("The client is not connected to any room");
        }

        new Thread(() -> {
            while (true) {
                try {
                    String fromServer = socket.readString();
                    String[] params = fromServer.split("\\s");
                    ServerMessage type = Enum.valueOf(ServerMessage.class, params[0]);

                    switch (type) {
                        case GAME_START:
                            if(listener instanceof PreGameListener)
                                ((PreGameListener) listener).onGameStart();
                            break;
                        case READY:
                            String[] split = params[1].split(",");
                            int current = Integer.parseInt(split[0]);
                            int max = Integer.parseInt(split[1]);

                            if(listener instanceof PreGameListener)
                                ((PreGameListener) listener).readyCount(current, max);
                            break;
                        case USER_LIST:
                            String[] nicknames = params[1].split(",");
                            if(listener instanceof PreGameListener)
                                ((PreGameListener) listener).receiveUserList(nicknames);
                            break;
                        case DISCONNECT:
                            String name = params[1];

                            listener.userDisconnect(name);
                            break;
                        case PLAY_DICE_ANIMATION:
                            int result = Integer.parseInt(params[1]);

                            if(listener instanceof PreRoundListener)
                                ((PreRoundListener) listener).playDiceAnimation(result);
                            break;
                        case END_DICE_ANIMATION:
                            if(listener instanceof PreRoundListener)
                                ((PreRoundListener) listener).transitionToPuzzle();
                            break;
                        case BEGIN_PUZZLE:
                            long finishUntil = Long.parseLong(params[1]);

                            if(listener instanceof InRoundListener)
                                ((InRoundListener) listener).beginPuzzle(finishUntil);
                            break;
                        case PLACEMENTS:
                            String[] foo = params[1].split(",");
                            ArrayList<PlayerPlacement> placements = new ArrayList<>();
                            for(String serialized : foo) {
                                placements.add(new PlayerPlacement(serialized));
                            }

                            if(listener instanceof InRoundListener)
                                ((InRoundListener) listener).placementsReceived(placements);
                            break;
                        case END_SCOREBOARD:
                            if(listener instanceof PostRoundListener)
                                ((PostRoundListener) listener).transitionToDice();
                            break;
                        case END_GAME:
                            if(listener instanceof  PostRoundListener)
                                ((PostRoundListener) listener).endGame();
                            break;
                        case ACCUSATION_RESULT:
                            String[] bar = params[1].split(",");
                            if(listener instanceof PostRoundListener)
                                ((PostRoundListener) listener).accusationResult(bar[0], bar[1], Boolean.parseBoolean(bar[2]), Integer.parseInt(bar[3]));
                            break;
                        default:
                            listener.unknownMessage(fromServer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    socket.close();
                    return;
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

        sendMessage(ClientMessage.READY, isReady);
    }

    /**
     * Ubongo!
     * @param bluff indicates whether the puzzle was solved (true for cheating)
     * @throws IOException
     */
    public void puzzleDone(boolean bluff) throws IOException {
        if (!isConnected) {
            throw new RuntimeException("Client is not connected");
        }

        sendMessage(ClientMessage.FINISHED_PUZZLE, bluff);
    }

    public void accuseOfCheating(String nick) throws IOException {
        if (!isConnected) {
            throw new RuntimeException("Client is not connected");
        }

        sendMessage(ClientMessage.ACCUSE, nick);
    }

    public void close() {
        try {
            sendMessage(ClientMessage.DISCONNECT);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ClientMessage type) throws IOException {
        sendMessage(type, null);
    }
    public void sendMessage(ClientMessage type, Object arg) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name());
        if(arg != null) {
            sb.append(" ");
            sb.append(arg);
        }
        socket.sendString(sb.toString());
    }

    /**
     * Sets a Listener to listen for messages from the server.
     * Only one listener can be registered at a time so this overwrites any previously registered Listener.
     * @param listener the Listener to be registered
     */
    public void registerListener(GeneralGameListener listener) {
        this.listener = listener;
    }

    public static GameClient getActiveGameClient() {
        return activeGameClient;
    }

    public static void setActiveGameClient(GameClient activeGameClient) {
        GameClient.activeGameClient = activeGameClient;
    }
}
