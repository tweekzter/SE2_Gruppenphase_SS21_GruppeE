package com.example.se2_gruppenphase_ss21;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.se2_gruppenphase_ss21.menu.JoinRoomFragment;
import com.example.se2_gruppenphase_ss21.menu.LocalGameFragment;
import com.example.se2_gruppenphase_ss21.menu.MenuActivity;
import com.example.se2_gruppenphase_ss21.networking.AvailableRoom;
import com.example.se2_gruppenphase_ss21.networking.MulticastReceiver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class GameLobbyUITest {

    private String userName;
    private String roomName;

    @Rule
    public ActivityTestRule<MenuActivity> mActivityRule =
            new ActivityTestRule<>(MenuActivity.class);

    @Before
    public void initValidString() {
        userName = "name";
        roomName = "room";
    }

    /**
     * simple test case that simulates the UI actions for creating a local game and tests if userName is passed correctly between Fragments and if gameRoom is created correctly
     *
     * @throws Exception
     */
    @Test
    public void testLocalGame() throws Exception {

        onView(withId(R.id.button_startGame)).perform(click());
        onView(withId(R.id.editTextUserName)).perform(typeText(userName));
        onView(withId(R.id.editTextUserName)).check(matches(withText("name")));

        onView(withId(R.id.button_localGame)).perform(click());

        Fragment localGameFragment = LocalGameFragment.newInstance(userName);
        String nameFromLocalGame = localGameFragment.getArguments().getString("param1");

        onView(withId(R.id.button_createRoom)).perform(click());

        onView(withId(R.id.editTextNameOfRoom)).perform(typeText(roomName));
        onView(withId(R.id.editTextMaxUsers)).perform(typeText("3"));

        onView(withId(R.id.button_startLocalServer)).perform(click());

        Fragment joinRoomFragment = JoinRoomFragment.newInstance(userName);
        String nameFromJoinRoom = joinRoomFragment.getArguments().getString("param1");

        if (!(nameFromJoinRoom.equals(nameFromLocalGame))) {
            throw new Exception();
        }

        List<AvailableRoom> rooms = MulticastReceiver.getRooms();

        String availableRoom = rooms.get(0).getName();

        if (!(availableRoom.equals(roomName))) {
            throw new Exception();
        }
    }


}