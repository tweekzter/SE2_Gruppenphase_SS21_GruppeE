package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.StrictMode;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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


    static AvailableRoom room;

    // Handler for creating post delay threads for updating ui
    Handler handler = new Handler();


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
    public static RoomFragment newInstance(String param1, AvailableRoom availableRoom) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        room = availableRoom;
        fragment.setArguments(args);
        // String userName = param1;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // force using ui thread for client connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

        TextView roomNameTextView = view.findViewById(R.id.textView_roomName);
        roomNameTextView.setText(room.getName()+"\n current Players:");

        String userName = getArguments().getString(ARG_PARAM1);

        // create new Client
        GameClient client = null;

        try {
            client = new GameClient(room, userName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.connect();

            // implementation of ServerMessageListener Interface that is used to respond to events that are noticed by server
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
                    updatePlayers(nicknames, view);
                }

                @Override
                public void unknownMessage(String message) {

                }
            };

            client.registerListener(listener);
            client.startReceiveLoop();
//          client.sendReady(true);
            // TODO: display roomname and users in this fragment... and num users and ready button; how to get users

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GameLogicException e) {
            e.printStackTrace();
        }

        return view;
    }

    /**
     * updates UI: shows a list of players that currently joined the room
     * @param nicknames all players that are in a given room
     * @param view of the fragment
     */
    public void updatePlayers(String[] nicknames, View view) {

        final Runnable r = new Runnable() {
            public void run() {

                LinearLayout layout = view.findViewById(R.id.layoutRoom);

                // reset layout that players don't appear many times
                layout.removeAllViews();

                // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(150/3), LinearLayout.LayoutParams.WRAP_CONTENT);

                //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(150/3), LinearLayout.LayoutParams.WRAP_CONTENT);
                //layout.setLayoutParams(lp);


                for (String n : nicknames) {

//                    TextView player;
//                    player = new TextView(getContext());
//                    player.setText(n);
//                    layout.addView(player);

                    Button newbtn;

                    newbtn = new Button(getContext());
                    newbtn.setText(n);
                    newbtn.setClickable(false);

                    // newbtn.setWidth(50);

                    layout.addView(newbtn);

                }
            }
        };

        handler.postDelayed(r, 1000);
    }
//
//    public String getRoomName(AvailableRoom room){
//        room.
//    }


}