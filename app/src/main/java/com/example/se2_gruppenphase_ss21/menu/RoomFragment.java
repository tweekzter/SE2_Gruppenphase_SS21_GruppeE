package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.ServerMessageListener;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, AvailableRoom r) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        String userName = param1;

        new Thread(new Runnable() {
            @Override
            public void run() {
                GameClient client = null;
                try {
                    client = new GameClient(r, userName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    client.connect();

                    ServerMessageListener listener = new ServerMessageListener() {
                        @Override
                        public void readyCount(int current, int max) {

                        }

                        @Override
                        public void onGameStart() {

                        }

                        @Override
                        public void userDisconnect(String nickname) {

                        }

                        @Override
                        public void receiveUserList(String[] nicknames) {

                        }

                        @Override
                        public void unknownMessage(String message) {

                        }
                    };


                    client.registerListener(listener);
                    client.startReceiveLoop();
//                  client.sendReady(true);
                    // TODO: display users

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GameLogicException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        return view;
    }
}