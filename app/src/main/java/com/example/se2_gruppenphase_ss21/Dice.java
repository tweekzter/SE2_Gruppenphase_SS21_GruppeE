package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Dice extends AppCompatActivity {
    Button wuerfel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        ImageView bildergebnis = findViewById(R.id.diceresult);
        AnimationDrawable imagesAnimation;
        bildergebnis.setBackgroundResource(R.drawable.dice_slideshow);
        imagesAnimation = (AnimationDrawable) bildergebnis.getBackground();
        imagesAnimation.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imagesAnimation.stop();
                test();
            }
        }, 3000);

    }



    private void test() {
        TextView ergebnis = findViewById(R.id.Ergebnis);
        ImageView bildergebnis = findViewById(R.id.diceresult);
        int zahl = (int) (Math.random() * 6 + 1);
        switch (zahl) {
            case 1:
                ergebnis.setText("Löwe");
                Toast.makeText(this, "Löwe", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.lion);
                break;
            case 2:
                ergebnis.setText("Hand");
                Toast.makeText(this, "Hand", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.hand);
                break;
            case 3:
                ergebnis.setText("Antilope");
                Toast.makeText(this, "Antilope", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.antilope);
                break;
            case 4:
                ergebnis.setText("Schlange");
                Toast.makeText(this, "Schlange", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.snake);
                break;
            case 5:
                ergebnis.setText("Elefant");
                Toast.makeText(this, "Elefant", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.elefant);
                break;
            case 6:
                ergebnis.setText("Käfer");
                Toast.makeText(this, "Käfer", Toast.LENGTH_SHORT).show();
                bildergebnis.setBackgroundResource(R.drawable.bug);
                break;

        }
    }
}