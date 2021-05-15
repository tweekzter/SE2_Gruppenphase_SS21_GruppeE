package com.example.se2_gruppenphase_ss21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;

import java.io.IOException;
import java.io.InputStream;

public class Tiles extends AppCompatActivity {
    int [] drawables = new int[25];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiles);
        try {
            InputStream is = getAssets().open("maps.xml");
            Boolean[][] map = XMLParser.parsexml("two","tet", is);


        } catch (IOException e) {

        }


    }
    private void fill(){
        drawables[0]=R.id.one;
        drawables[1]=R.id.two;
        drawables[2] = R.id.three;
        drawables[3]=R.id.four;
        drawables[4] = R.id.five;
        drawables[5] = R.id.six;
        drawables[6] = R.id.seven;
        drawables[7] = R.id.eigth;
        drawables[8] = R.id.nine;
        drawables[9] = R.id.ten;
        drawables[10] = R.id.eleven;
        drawables[11] = R.id.twelve;
        drawables[12] = R.id.thirteen;
        drawables[13] = R.id.fourteen;
        drawables[14] = R.id.fifteen;
        drawables[15] = R.id.sixteen;
        drawables[16] = R.id.seventeen;
        drawables[17]= R.id.eighteen;
        drawables[18] = R.id.nineteen;
        drawables[19] = R.id.twenty;
        drawables[20] = R.id.twentyone;
        drawables[21] = R.id.twentytwo;
        drawables[22] = R.id.twentythree;
        drawables[23] = R.id.twentyfour;
        drawables[24] = R.id.twentyfive;

    }
}