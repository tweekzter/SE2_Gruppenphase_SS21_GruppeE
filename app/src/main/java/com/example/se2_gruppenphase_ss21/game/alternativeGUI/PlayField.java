package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.R;

public class PlayField extends Fragment {
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup puzzleContainer,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playfield, puzzleContainer, false);
        TextView text = view.findViewById(R.id.testi);
        view.findViewById(R.id.table_playfield).setOnTouchListener((v, event) -> {
            int x = (int)convertPixelsToDp(event.getX()) / 76;
            int y = (int)convertPixelsToDp(event.getY()) / 76;
            getField(x,y).setBackgroundColor(Color.RED);

            return true;
        });
        view.findViewById(R.id.field20).setOnClickListener(v -> text.setText("cougar"));
        return view;
    }

    private float convertDpToPixels(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    private float convertPixelsToDp(float pixels) {
        return pixels / getContext().getResources().getDisplayMetrics().density;
    }

    private View getField(int x, int y) {
        String search = x+","+y;
        switch(search) {
            case("0,0"):
                return getView().findViewById(R.id.field00);
            case("1,0"):
                return getView().findViewById(R.id.field10);
            case("2,0"):
                return getView().findViewById(R.id.field20);
            case("3,0"):
                return getView().findViewById(R.id.field30);
            case("4,0"):
                return getView().findViewById(R.id.field40);
            case("0,1"):
                return getView().findViewById(R.id.field01);
            case("1,1"):
                return getView().findViewById(R.id.field11);
            case("2,1"):
                return getView().findViewById(R.id.field21);
            case("3,1"):
                return getView().findViewById(R.id.field31);
            case("4,1"):
                return getView().findViewById(R.id.field41);
            case("0,2"):
                return getView().findViewById(R.id.field02);
            case("1,2"):
                return getView().findViewById(R.id.field12);
            case("2,2"):
                return getView().findViewById(R.id.field22);
            case("3,2"):
                return getView().findViewById(R.id.field32);
            case("4,2"):
                return getView().findViewById(R.id.field42);
            case("0,3"):
                return getView().findViewById(R.id.field03);
            case("1,3"):
                return getView().findViewById(R.id.field13);
            case("2,3"):
                return getView().findViewById(R.id.field23);
            case("3,3"):
                return getView().findViewById(R.id.field33);
            case("4,3"):
                return getView().findViewById(R.id.field43);
            case("0,4"):
                return getView().findViewById(R.id.field04);
            case("1,4"):
                return getView().findViewById(R.id.field14);
            case("2,4"):
                return getView().findViewById(R.id.field24);
            case("3,4"):
                return getView().findViewById(R.id.field34);
            case("4,4"):
                return getView().findViewById(R.id.field44);
            default:
                return getView().findViewById(R.id.field44);
        }
    }
}
