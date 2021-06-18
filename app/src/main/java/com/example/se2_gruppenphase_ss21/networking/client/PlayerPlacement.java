package com.example.se2_gruppenphase_ss21.networking.client;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerPlacement implements Parcelable {
    private String nickname;
    private int placement;
    private int points;
    private long timeTaken;
    private boolean didFinnish;

    public PlayerPlacement(String serialized) {
        String[] split = serialized.split(":");
        nickname = split[0];
        placement = Integer.parseInt(split[1]);
        points = Integer.parseInt(split[2]);
        didFinnish = Boolean.parseBoolean(split[3]);
        timeTaken = Long.parseLong(split[4]);
    }

    protected PlayerPlacement(Parcel in) {
        nickname = in.readString();
        placement = in.readInt();
        points = in.readInt();
        timeTaken = in.readLong();
        didFinnish = in.readByte() != 0;
    }

    public static final Creator<PlayerPlacement> CREATOR = new Creator<PlayerPlacement>() {
        @Override
        public PlayerPlacement createFromParcel(Parcel in) {
            return new PlayerPlacement(in);
        }

        @Override
        public PlayerPlacement[] newArray(int size) {
            return new PlayerPlacement[size];
        }
    };

    public String getNickname() {
        return nickname;
    }

    public int getPlacement() {
        return placement;
    }

    public int getPoints() {
        return points;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public boolean isDidFinnish() {
        return didFinnish;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeInt(placement);
        dest.writeInt(points);
        dest.writeLong(timeTaken);
        dest.writeByte((byte) (didFinnish ? 1 : 0));
    }
}
