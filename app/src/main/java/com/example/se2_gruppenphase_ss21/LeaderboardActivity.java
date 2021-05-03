package com.example.se2_gruppenphase_ss21;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new LeaderboardFragment()).commit();

        Button newGameButton = findViewById(R.id.button_newGame);
        newGameButton.setOnClickListener((View v) ->{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new StartGameFragment()).addToBackStack("tag").commit();
        });
    }
}
