package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MapAndTileTest {
    private Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void testReadFirstMapFromFile() {

        boolean[][] expected = {
                { false, false, false, false },
                { false, true,  true,  false },
                { false, true,  true,  false },
                { false, false, false, false }
        };

        Map map = new Map();
        map.loadMaps(appContext.getAssets());
        assertArrayEquals(expected, map.getMapByID(1));
    }

    @Test
    public void testReadMapFromFile() {

        boolean[][] expected = {
                { false, false, false, false, true },
                { true,  true,  false, false, false },
                { true,  false, false, false, false }
        };

        Map map = new Map();
        map.loadMaps(appContext.getAssets());
        assertArrayEquals(expected, map.getMapByID(3));
    }
}