package com.example.se2_gruppenphase_ss21.menu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_gruppenphase_ss21.R;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new LeaderboardFragment()).commit();
    }
}
