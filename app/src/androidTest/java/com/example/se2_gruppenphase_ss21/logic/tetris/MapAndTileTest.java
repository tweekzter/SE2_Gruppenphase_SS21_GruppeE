package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.se2_gruppenphase_ss21.game.XMLParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;

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
    public void testLoadTile() {
        Tile tile = new Tile(appContext.getAssets(), -1, "standard");
        Position[] shape = tile.getShape();

        assertEquals(-1, shape[0].x); assertEquals(-2, shape[0].y);
        assertEquals(0, shape[1].x); assertEquals(-1, shape[1].y);
        assertEquals(-1, shape[2].x); assertEquals(0, shape[2].y);
        assertEquals(0, shape[3].x); assertEquals(0, shape[3].y);
        assertEquals(1, shape[4].x); assertEquals(0, shape[4].y);
        assertEquals(0, shape[5].x); assertEquals(1, shape[5].y);
        assertEquals(1, shape[6].x); assertEquals(2, shape[6].y);
    }

    @Test
    public void testGetStructure() {
        boolean[][] result = StructureLoader.getStructure(appContext.getAssets(), -1, "map", "5x6");

        boolean[][] expected = {
                { false, false, false, false },
                { false, true,  true,  false },
                { false, true,  true,  false },
                { false, false, false, false }
        };

        assertArrayEquals(expected, result);
    }

    @Test
    public void testCenterTile() {
        Tile tile = new Tile();
        tile.addPoint(0,0); tile.addPoint(1,1);
        tile.addPoint(1,2); tile.addPoint(1,3);
        tile.addPoint(2,4);

        tile.centerTile();

        Position[] expected = new Position[5];
        expected[0] = new Position(-1,-2);
        expected[1] = new Position(0,-1);
        expected[2] = new Position(0,0);
        expected[3] = new Position(0,1);
        expected[4] = new Position(1,2);

        Position[] shape = tile.getShape();
        assertArrayEquals(expected, shape);
    }

    @Test
    public void testGetTiles() {
        int[] actual = StructureLoader.getTiles(appContext.getAssets(), "lion", 3);
        int[] expected = { 9, 10, 5 };
        assertArrayEquals(expected, actual);

        actual = StructureLoader.getTiles(appContext.getAssets(), "hand", 5);
        int[] expected2 = { 6, 5, 7 };
        assertArrayEquals(expected2, actual);

        actual = StructureLoader.getTiles(appContext.getAssets(), "bug", 21);
        int[] expected3 = { 2, 6, 5 };
        assertArrayEquals(expected3, actual);
    }
}