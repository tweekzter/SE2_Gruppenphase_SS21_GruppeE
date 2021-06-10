package com.example.se2_gruppenphase_ss21.game.alternativeGUI;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;

import com.example.se2_gruppenphase_ss21.R;
import com.example.se2_gruppenphase_ss21.menu.StartGameFragment;
import com.example.se2_gruppenphase_ss21.networking.Util;
import com.example.se2_gruppenphase_ss21.networking.client.GameClient;
import com.example.se2_gruppenphase_ss21.networking.client.listeners.PreRoundListener;


public class Dice extends Fragment implements PreRoundListener {

    private GameClient client;

    private static final int ANTILOPE_POS = 325;
    private static final int LION_POS = 775;
    private static final int ELEPHANT_POS = 1225;
    private static final int BUG_POS = 1675;
    private static final int HAND_POS = 2125;
    private static final int SNAKE_POS = 2575;

    private static final int ANTILOPE_ID = 1;
    private static final int LION_ID = 2;
    private static final int ELEPHANT_ID = 3;
    private static final int BUG_ID = 4;
    private static final int HAND_ID = 5;
    private static final int SNAKE_ID = 6;

    private int diceResult = 325;
    private int animationTime = 5000;



    public View onCreateView(LayoutInflater inflater, ViewGroup puzzleContainer,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dice, puzzleContainer, false);
        return view;
    }

    public void onStart() {
        super.onStart();
        client = GameClient.getActiveGameClient();
        //Handler handler = new Handler(Looper.getMainLooper());
        //handler.postDelayed(() -> animateDiceRoll(), 500);
    }

    private void animateDiceRoll(int diceResultPos) {
        TableLayout frame = getView().findViewById(R.id.frame_roll);
        float distance = frame.getWidth();

        ObjectAnimator rollAnimation = ObjectAnimator.ofFloat(frame, "translationX", distance);
        rollAnimation.setInterpolator(new LinearInterpolator());
        rollAnimation.setDuration(animationTime/2);

        ObjectAnimator reset = ObjectAnimator.ofFloat(frame, "translationX", 0f);
        reset.setInterpolator(new LinearInterpolator());
        reset.setDuration(0);

        float target = convertDpToPixels(diceResultPos);
        ObjectAnimator stop = ObjectAnimator.ofFloat(frame, "translationX", target);
        stop.setInterpolator(new DecelerateInterpolator());
        stop.setDuration(animationTime/2);
        Log.d("dice", "ratio: "+target/distance);

        AnimatorSet animation = new AnimatorSet();
        animation.playSequentially(rollAnimation, reset, stop);
        animation.start();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.puzzle_container, new PlayField())
                    .commit();
            }, animationTime + 1000);
    }



    private float convertDpToPixels(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    private float convertPixelsToDp(float pixels) {
        return pixels / getContext().getResources().getDisplayMetrics().density;
    }

    private int getDiceResultPos(int result) {
        switch(result) {
            case(LION_ID):
                return LION_POS;
            case(ELEPHANT_ID):
                return ELEPHANT_POS;
            case(BUG_ID):
                return BUG_POS;
            case(HAND_ID):
                return HAND_POS;
            case(SNAKE_ID):
                return SNAKE_POS;
            default:
                return ANTILOPE_POS;
        }
    }

    @Override
    public void playDiceAnimation(int result) {
        Handler handler = new Handler(Looper.getMainLooper());
        int diceResultPos = getDiceResultPos(result);
        handler.post(() -> animateDiceRoll(diceResultPos));
    }

    @Override
    public void transitionToPuzzle() {

    }

    @Override
    public void userDisconnect(String nickname) {

    }

    @Override
    public void unknownMessage(String message) {

    }
}
