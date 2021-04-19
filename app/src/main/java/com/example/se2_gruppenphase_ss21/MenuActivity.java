package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        settingsButton = findViewById(R.id.button_settings);

        settingsButton.setOnClickListener((View v) -> {
            openSettingsFragment();
        });
    }

    public void openSettingsFragment(){
        System.out.println("opening settings fragment");

        getSupportFragmentManager().beginTransaction().add(R.id.container, new SettingsFragment()).commit();
    }
}