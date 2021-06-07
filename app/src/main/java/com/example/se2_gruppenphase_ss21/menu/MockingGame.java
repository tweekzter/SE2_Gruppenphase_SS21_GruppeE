package com.example.se2_gruppenphase_ss21.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;

public class MockingGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocking_game);

        Intent i = getIntent();
        GameClient client = (GameClient) i.getParcelableExtra("client");

        System.out.println(client + " !!!!!");
    }
}