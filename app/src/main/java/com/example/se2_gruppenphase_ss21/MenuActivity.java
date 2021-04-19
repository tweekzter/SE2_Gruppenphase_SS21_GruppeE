package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

//    Button settingsButton;
    Button rulesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        rulesButton = findViewById(R.id.button_gameRules);

        rulesButton.setOnClickListener((View v) -> {
            openRulesActivity();
        });


//        settingsButton = findViewById(R.id.button_settings);
//
//        settingsButton.setOnClickListener((View v) -> {
//            openSettingsFragment();
//        });

//        getSupportFragmentManager().beginTransaction().add(R.id.container, new MenuFragment()).commit();
    }

    public void openRulesActivity(){
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

//    public void openSettingsFragment(){
//        System.out.println("opening settings fragment");
//    }
}