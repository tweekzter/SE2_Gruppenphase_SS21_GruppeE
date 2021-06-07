package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.R;


public class Dice extends Fragment {

    private static final int ANTILOPE_POS = 325;
    private static final int LION_POS = 775;
    private static final int ELEPHANT_POS = 1225;
    private static final int BUG_POS = 1675;
    private static final int HAND_POS = 2125;
    private static final int SNAKE_POS = 2575;
    private int animationTime = 5000;



    public View onCreateView(LayoutInflater inflater, ViewGroup puzzleContainer,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dice, puzzleContainer, false);
        Button b = view.findViewById(R.id.roll_dice);
        b.setOnClickListener(v -> animateDiceRoll());
        return view;
    }

    private void animateDiceRoll() {
        TableLayout frame = getView().findViewById(R.id.frame_roll);
        float distance = frame.getWidth();

        ObjectAnimator rollAnimation = ObjectAnimator.ofFloat(frame, "translationX", distance);
        rollAnimation.setInterpolator(new LinearInterpolator());
        rollAnimation.setDuration(animationTime/2);

        ObjectAnimator reset = ObjectAnimator.ofFloat(frame, "translationX", 0f);
        reset.setInterpolator(new LinearInterpolator());
        reset.setDuration(0);

        float target = convertDpToPixels(getDiceResult());
        ObjectAnimator stop = ObjectAnimator.ofFloat(frame, "translationX", target);
        stop.setInterpolator(new DecelerateInterpolator());
        stop.setDuration(animationTime/2);
        Log.d("dice", "ratio: "+target/distance);

        AnimatorSet animation = new AnimatorSet();
        animation.playSequentially(rollAnimation, reset, stop);
        animation.start();


        new Thread(() -> {
            try {
                Thread.sleep(animationTime+1000);
                Log.d("dice", "pos: "+convertPixelsToDp(frame.getTranslationX()));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }



    private float convertDpToPixels(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    private float convertPixelsToDp(float pixels) {
        return pixels / getContext().getResources().getDisplayMetrics().density;
    }

    private int getDiceResult() {
        int[] diceCounts = { ANTILOPE_POS, LION_POS, ELEPHANT_POS, BUG_POS, HAND_POS, SNAKE_POS };
        return diceCounts[(int)(diceCounts.length * Math.random())];
    }

    public int getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(int time) {
        animationTime = time;
    }
}
