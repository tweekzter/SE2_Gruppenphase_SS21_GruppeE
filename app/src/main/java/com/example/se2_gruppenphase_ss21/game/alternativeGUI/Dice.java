package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.R;

public class Dice extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup puzzleContainer,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dice, puzzleContainer, false);
        Button b = view.findViewById(R.id.roll_dice);
        b.setOnClickListener(v -> animateDiceRoll());
        return view;
    }

    private void animateDiceRoll() {
        TableLayout frame = getView().findViewById(R.id.frame_roll);
        ObjectAnimator animator = ObjectAnimator.ofFloat(frame, "translationX", 7700);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(4000);
        animator.setRepeatCount(5);
        animator.start();

        new Thread(() -> {
            int time = 0;
            while(time < 5000) {
                Log.d("dicebar", "loc: "+frame.getTranslationX());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time += 500;
            }
        }).start();
    }
}
