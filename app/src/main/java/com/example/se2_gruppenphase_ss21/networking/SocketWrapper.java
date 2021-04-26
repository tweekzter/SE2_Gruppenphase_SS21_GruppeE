package com.example.se2_gruppenphase_ss21.networking;

import java.io.*;
import java.net.Socket;

public class SocketWrapper {

    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    public SocketWrapper(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() throws IOException {
        socket.close();
    }

    /**
     * Send a specified string. This method auto-flushes the underlying BufferedStream.
     *
     * @param msg the string to be sent
     * @throws IOException
     */
    public void sendString(String msg) throws IOException {
        out.writeUTF(msg);
        out.flush();
    }

    public String readString() throws IOException {
        return in.readUTF();
    }
}