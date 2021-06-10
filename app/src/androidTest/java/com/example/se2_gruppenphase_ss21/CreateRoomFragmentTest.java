package com.example.se2_gruppenphase_ss21;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.se2_gruppenphase_ss21.menu.CreateRoomFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CreateRoomFragmentTest {

    @Test
    public void testCreateRoom() {
        FragmentScenario scenario = FragmentScenario.launchInContainer(CreateRoomFragment.class, Bundle.EMPTY);

        onView(withId(R.id.editTextNameOfRoom))
                .perform(typeText("testa"), closeSoftKeyboard());

        onView(withId(R.id.editTextMaxUsers))
                .perform(typeText("zwei"), closeSoftKeyboard());

        onView(withId(R.id.editTextMaxUsers))
                .check(matches(withText("")));

        onView(withId(R.id.editTextMaxUsers))
                .perform(typeText("4"), closeSoftKeyboard());

        onView(withId(R.id.editTextMaxUsers))
                .check(matches(withText("4")));

    }

}
