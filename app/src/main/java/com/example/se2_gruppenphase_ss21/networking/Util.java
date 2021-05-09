package com.example.se2_gruppenphase_ss21.networking;

public class Util {
    public static void sleep(int seconds, long millis) {
        try {
            Thread.currentThread().wait(seconds * 1000 + millis);
        }catch (InterruptedException ie) {
            ie.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
