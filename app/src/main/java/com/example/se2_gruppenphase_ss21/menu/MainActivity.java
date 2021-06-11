package com.example.se2_gruppenphase_ss21.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.game.Dice;

public class MainActivity extends AppCompatActivity {


    Button gettingStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_welcome);

        gettingStartedButton = findViewById(R.id.button_getStarted);

        gettingStartedButton.setOnClickListener((View v) -> {
            openMenuActivity();
        });
    }

    public void openMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}