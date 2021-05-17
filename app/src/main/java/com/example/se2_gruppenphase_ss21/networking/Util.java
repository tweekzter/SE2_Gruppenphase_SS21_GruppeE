package com.example.se2_gruppenphase_ss21.networking;

public class Util {

    private Util() {}

    public static void sleep(int seconds, long millis) {
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(seconds * 1000 + millis);
            }
        }catch (InterruptedException ie) {
            ie.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
