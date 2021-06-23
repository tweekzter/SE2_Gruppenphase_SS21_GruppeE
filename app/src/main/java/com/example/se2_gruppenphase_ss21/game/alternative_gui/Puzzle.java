package com.example.se2_gruppenphase_ss21.game.alternative_gui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.se2_gruppenphase_ss21.R;
import java.util.Objects;

/**
 * Represents the Puzzle and contains of the DICE and the PLAYFIELD itself.
 * Dice & PlayField are represented in separate fragments.
 *
 * @author Manuel Simon #00326348
 */
public class Puzzle extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_puzzle);
        Objects.requireNonNull(getSupportActionBar()).hide();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.puzzle_container, new Dice())
                .commit();
    }
}
