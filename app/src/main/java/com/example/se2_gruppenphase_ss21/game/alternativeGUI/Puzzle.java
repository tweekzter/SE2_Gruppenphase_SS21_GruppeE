package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.se2_gruppenphase_ss21.R;
import java.util.Objects;

public class Puzzle extends AppCompatActivity {
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_puzzle);
        Objects.requireNonNull(getSupportActionBar()).hide();

        getSupportFragmentManager().beginTransaction().add(R.id.puzzle_container, new Dice()).commit();
    }
}
