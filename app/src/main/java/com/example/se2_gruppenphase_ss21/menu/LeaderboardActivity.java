package com.example.se2_gruppenphase_ss21.menu;

import com.example.se2_gruppenphase_ss21.R;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new LeaderboardFragment()).commit();
    }
}
