package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button gettingStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        try
//        {
            this.getSupportActionBar().hide();
//        }
//        catch (NullPointerException e){}


        setContentView(R.layout.activity_welcome);

        gettingStartedButton = findViewById(R.id.button_getStarted);
//        gettingStartedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        gettingStartedButton.setOnClickListener((View v) -> {
            openMenuActivity();
        });
    }

    public void openMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}