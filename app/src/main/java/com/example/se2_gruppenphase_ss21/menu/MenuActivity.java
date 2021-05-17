package com.example.se2_gruppenphase_ss21.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.se2_gruppenphase_ss21.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new MenuFragment()).commit();

    }

}