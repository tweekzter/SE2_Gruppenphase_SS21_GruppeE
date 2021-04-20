package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        // TODO: replace the following fragment with MenuFragment (which has to be created!)
        getSupportFragmentManager().beginTransaction().add(R.id.container, new MenuFragment()).commit();

    }

//    public void openRulesActivity() {
//        Intent intent = new Intent(this, RulesActivity.class);
//        startActivity(intent);
//    }
}