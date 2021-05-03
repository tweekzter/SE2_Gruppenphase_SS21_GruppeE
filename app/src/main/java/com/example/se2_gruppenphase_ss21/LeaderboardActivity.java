package com.example.se2_gruppenphase_ss21;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private PlayerArrayAdapter playerArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.fragment_leaderboard);

        //getSupportFragmentManager().beginTransaction().add(R.id.container, new LeaderboardFragment()).commit();

        /*Button newGameButton = findViewById(R.id.button_newGame);
        newGameButton.setOnClickListener((View v) ->{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new StartGameFragment()).addToBackStack("tag").commit();
        });*/

        listView = (ListView) findViewById(R.id.listView);
        playerArrayAdapter = new PlayerArrayAdapter(getApplicationContext(), R.layout.listview_row_layout);
        listView.setAdapter(playerArrayAdapter);
        List<String[]> playerList = readData();
        for(String[] playerData:playerList){
            String position = playerData[0];
            String playername = playerData[1];
            String points = playerData[2];

            Player player = new Player(position, playername, points);

            playerArrayAdapter.add(player);
        }
    }

    //TODO: Edit to use for real players
    //List of player to test the design of the ListView
    private List<String[]> readData() {
        List<String[]> resultList = new ArrayList<String[]>();
        String[] player1 = new String[3];
        player1[0] = "1";
        player1[1] = "Testperson1";
        player1[2] = "4 Punkte";
        resultList.add(player1);

        String[] player2 = new String[3];
        player2[0] = "2";
        player2[1] = "Testperson2";
        player2[2] = "3 Punkte";
        resultList.add(player2);

        String[] player3 = new String[3];
        player3[0] = "3";
        player3[1] = "Testperson3";
        player3[2] = "2 Punkte";
        resultList.add(player3);

        String[] player4 = new String[3];
        player4[0] = "4";
        player4[1] = "Testperson4";
        player4[2] = "1 Punkte";
        resultList.add(player4);

        String[] player5 = new String[3];
        player5[0] = "5";
        player5[1] = "Testperson5";
        player5[2] = "0 Punkte";
        resultList.add(player5);

        return resultList;
    }
}
