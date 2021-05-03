package com.example.se2_gruppenphase_ss21;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_welcome);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new LeaderboardFragment()).commit();
    }
}
