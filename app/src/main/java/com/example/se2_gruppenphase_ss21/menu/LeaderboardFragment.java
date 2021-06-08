package com.example.se2_gruppenphase_ss21.menu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.Player;
import com.example.se2_gruppenphase_ss21.PlayerArrayAdapter;
import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.client.ServerMessageListenerImpl;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.InRoundListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LeaderboardFragment extends Fragment {
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
    static String[] nicknameslist = null;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Rules1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String param1, AvailableRoom availableRoom, String[] nicknames) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putString(LEADERBOARD_ARG_PARAM1, param1);
        room = availableRoom;
        nicknameslist = nicknames;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

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

        Button nextGameButton = view.findViewById(R.id.buttonNextRound);
        nextGameButton.setOnClickListener((View v) ->{
            Intent intent = new Intent(getActivity(), MockingGame.class);
            startActivity(intent);

        });

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
}
