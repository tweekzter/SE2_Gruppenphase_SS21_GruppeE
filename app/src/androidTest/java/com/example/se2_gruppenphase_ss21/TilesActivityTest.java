package com.example.se2_gruppenphase_ss21;

import android.view.View;

import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.se2_gruppenphase_ss21.game.Tiles;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class TilesActivityTest {

    @Rule
    public ActivityScenarioRule<Tiles> activityRule
            = new ActivityScenarioRule<>(Tiles.class);

    @Test
    public void testPlaceTile() {


        onView(withId(R.id.ubongo))
                .perform(click());


    }


}
