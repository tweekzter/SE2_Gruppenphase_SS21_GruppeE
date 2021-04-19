package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_rules);

//        settingsButton = findViewById(R.id.button_settings);
//
//        settingsButton.setOnClickListener((View v) -> {
//            openSettingsFragment();
//        });

        getSupportFragmentManager().beginTransaction().add(R.id.container, new Rules1Fragment()).commit();
    }
}