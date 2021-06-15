package com.example.se2_gruppenphase_ss21.networking;

import java.io.*;
import java.net.Socket;

public class SocketWrapper {

    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    /**
     * A simple Wrapper for a Socket, that enables reading and writing data from and to the Socket.
     * @param socket the original Socket
     * @throws IOException
     */
    public SocketWrapper(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a specified string. This method auto-flushes the underlying BufferedStream.
     * @param msg the string to be sent
     * @throws IOException
     */
    public void sendString(String msg) throws IOException {
        out.writeUTF(msg);
        out.flush();
    }

    /**
     * Reads a string from the underlying Socket.
     * @return the read string
     * @throws IOException
     */
    public String readString() throws IOException {
        return in.readUTF();
    }
}