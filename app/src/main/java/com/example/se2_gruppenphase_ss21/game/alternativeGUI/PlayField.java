package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.R;

public class PlayField extends Fragment {
    private int[] tiles;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tiles = getArguments().getIntArray("tiles");
    }

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup puzzleContainer,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playfield, puzzleContainer, false);
        view.findViewById(R.id.table_playfield).setOnTouchListener((v, event) -> {
            int x = (int)convertPixelsToDp(event.getX()) / 76;
            int y = (int)convertPixelsToDp(event.getY()) / 76;
            TableLayout table = getView().findViewById(R.id.table_playfield);
            TableRow row = (TableRow)table.getChildAt(y);
            row.getChildAt(x).setBackgroundColor(Color.RED);

            return true;
        });

        return view;
    }

    private float convertDpToPixels(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    private float convertPixelsToDp(float pixels) {
        return pixels / getContext().getResources().getDisplayMetrics().density;
    }
}
