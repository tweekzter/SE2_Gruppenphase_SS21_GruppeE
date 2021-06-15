package com.example.se2_gruppenphase_ss21.menu;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.Player;
import com.example.se2_gruppenphase_ss21.PlayerArrayAdapter;
import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.game.Dice;
import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.PlayerPlacement;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.GeneralGameListener;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.PostRoundListener;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.PreGameListener;
import com.example.se2_gruppenphase_ss21.networking.server.logic.GameLogicException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */

public class LeaderboardFragment extends Fragment
        implements PostRoundListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LEADERBOARD_ARG_PARAM1 = "param1";
    private static final String LEADERBOARD_ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String leaderboardmParam1;
    private String leaderboardmParam2;


    private PlayerArrayAdapter playerArrayAdapter;
    private ListView listView;
    static AvailableRoom room;
    static Map<String, Integer> nicknamesMap;
    static boolean isReady = false;
    Handler handler = new Handler();

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);


        ArrayList<PlayerPlacement> placements = getActivity().getIntent().getParcelableArrayListExtra("key");

        if (getArguments() != null) {
            leaderboardmParam1 = getArguments().getString(LEADERBOARD_ARG_PARAM1);
            leaderboardmParam2 = getArguments().getString(LEADERBOARD_ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        Button quitGameButton = view.findViewById(R.id.buttonQuitGame);
        quitGameButton.setOnClickListener((View v) ->{
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            getParentFragmentManager().beginTransaction().replace(R.id.container, new MenuFragment()).addToBackStack("tag").commit();
        });


        String userName;
        if(getArguments() != null) {
            userName = getArguments().getString(LEADERBOARD_ARG_PARAM1);
        }

        GameClient gameClient = GameClient.getActiveGameClient();
        gameClient.registerListener(this);




        listView = (ListView) view.findViewById(R.id.listView);
        playerArrayAdapter = new PlayerArrayAdapter(view.getContext(), R.layout.listview_row_layout);
        listView.setAdapter(playerArrayAdapter);

        //TODO: Implement
        Map<String, Integer> usermap = null;

        List<String[]> playerList = readData(usermap);
        for(String[] playerData:playerList){
            String position = playerData[0];
            String playername = playerData[1];
            String points = playerData[2];

            Player player = new Player(position, playername, points);

            playerArrayAdapter.add(player);
        }


        return view;
    }

    //List of players
    private List<String[]> readData(Map<String, Integer> map) {
        List<String[]> resultList = new ArrayList<String[]>();
        if (map != null){
            List<String> nicknames = new ArrayList<>(map.keySet());
            List<Integer> placements = new ArrayList<>(map.values());
            for (int i = 0; i < map.size(); i++) {
                String[] player = new String[3];
                player[0] = Integer.toString(i+1);
                player[1] = nicknames.get(i);
                player[2] = Integer.toString(placements.get(i));
                resultList.add(player);
            }
        }
        return resultList;
    }

    public void updateReady(int current, int max, View view) {

        final Runnable runUpdateReady = new Runnable() {
            public void run() {

                LinearLayout layout = view.findViewById(R.id.layoutRoom);
                layout.removeAllViews();

                layout.setPadding(450, 200, 450, 200);

                Button readyData;
                readyData = new Button(getContext());
                readyData.setText(String.valueOf(current) + "/" + String.valueOf(max));
                readyData.setClickable(false);
                layout.addView(readyData);

            }
        };

        handler.postDelayed(runUpdateReady, 1000);
    }

    @Override
    public void transitionToDice() {

        Intent intent = new Intent(getActivity(), Dice.class);
        startActivity(intent);

    }

    @Override
    public void endGame() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void accusationResult(String accuser, String accused, boolean wasCheating, int pointLoss) {


    }

    @Override
    public void userDisconnect(String nickname) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() ->
                Toast.makeText(getActivity(), "Player "+nickname+" disconnected!", Toast.LENGTH_LONG).show()
        );

    }

    @Override
    public void unknownMessage(String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() ->
                Toast.makeText(getActivity(), "Network error: "+message, Toast.LENGTH_LONG).show()
        );

    }
}
