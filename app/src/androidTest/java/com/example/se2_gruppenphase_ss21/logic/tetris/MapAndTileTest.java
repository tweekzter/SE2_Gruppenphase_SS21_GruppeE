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
        boolean[][] result = StructureLoader.getStructure(appContext.getAssets(), -1, "map", "5x5");

        boolean[][] expected = {
                { false, true,  true,  false },
                { false, true,  true,  false },
                { false, true,  true,  false },
                { false, false, false, false }
        };

        assertArrayEquals(expected, result);
    }

    @Test
    public void loadTile() {
        Tile tile = new Tile(appContext.getAssets(), 0, "standard");

        assertEquals(-1, tile.getShape()[0].x);
        assertEquals(0, tile.getShape()[1].x);

        tile.setTileByID(appContext.getAssets(), 2, "standard");

        assertEquals(0, tile.getShape()[0].x);
        assertEquals(-1, tile.getShape()[0].y);
        assertEquals(-1, tile.getShape()[1].x);
        assertEquals(0, tile.getShape()[1].y);
        assertEquals(0, tile.getShape()[2].x);
        assertEquals(0, tile.getShape()[2].y);
        assertEquals(1, tile.getShape()[3].x);
        assertEquals(0, tile.getShape()[3].y);
    }

    @Test
    public void testMapLoad() {
        Map map = new Map(appContext.getAssets(), -1, "5x5");

        boolean[][] expected = {
                { false, true,  true,  false },
                { false, true,  true,  false },
                { false, true,  true,  false },
                { false, false, false, false }
        };

        boolean[][] actual = new boolean[map.getSizeY()][map.getSizeX()];
        for(int y=0; y < map.getSizeY(); y++) {
            for(int x=0; x < map.getSizeX(); x++) {
                if(map.getBox(x,y).isField())
                    actual[y][x] = true;
            }
        }

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testInvalidStructureLoad() {
        boolean[][] standardStructure = {
                { false, false, false, false },
                { false, true,  true,  false },
                { false, true,  true,  false },
                { false, false, false, false }
        };

        boolean[][] actual = StructureLoader.getStructure(appContext.getAssets(), -12, "map", "5x5");

        assertArrayEquals(standardStructure, actual);
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