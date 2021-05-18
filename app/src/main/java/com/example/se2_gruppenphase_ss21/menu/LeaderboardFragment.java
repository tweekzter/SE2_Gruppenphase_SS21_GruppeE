package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.Player;
import com.example.se2_gruppenphase_ss21.PlayerArrayAdapter;
import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.MulticastReceiver;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.ServerMessageListener;
import com.example.se2_gruppenphase_ss21.networking.server.GameServer;

import java.util.ArrayList;
import java.util.List;

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

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param leaderboardparam1 Parameter 1.
     * @return A new instance of fragment Rules1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String leaderboardparam1, AvailableRoom room) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putString(LEADERBOARD_ARG_PARAM1, leaderboardparam1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
            getParentFragmentManager().beginTransaction().replace(R.id.container, new MenuFragment()).addToBackStack("tag").commit();
        });

        Button nextGameButton = view.findViewById(R.id.buttonNextRound);
        nextGameButton.setOnClickListener((View v) ->{
            getParentFragmentManager().beginTransaction().replace(R.id.container, new RoomFragment()).addToBackStack("tag").commit();
        });

        //To Do: Listener and Array for the server nicknames

        listView = (ListView) view.findViewById(R.id.listView);
        playerArrayAdapter = new PlayerArrayAdapter(view.getContext(), R.layout.listview_row_layout);
        listView.setAdapter(playerArrayAdapter);
        String[] nicknames = {"Test1", "Test2"};
        List<String[]> playerList = readData(nicknames);
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
    private List<String[]> readData(String[] nicknames) {
        List<String[]> resultList = new ArrayList<String[]>();
        int points = 4;
        for (int i = 0; i < nicknames.length; i++) {
            String[] player = new String[3];
            player[0] = Integer.toString(i+1);
            player[1] = nicknames[i];
            player[2] = points + " Punkte";
            if(points>=0) points--;
            resultList.add(player);
        }
        return resultList;
    }
}
