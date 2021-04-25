package com.example.se2_gruppenphase_ss21.networking.server.logic;

public enum GameRoomState {
    WAITING {
        @Override public String toString() {
            return "waiting";
        }
    },
    PLAYING {
        @Override public String toString() {
            return "playing";
        }
    },
    RESTARTING {
        @Override public String toString() {
            return "restarting";
        }
    },
}
