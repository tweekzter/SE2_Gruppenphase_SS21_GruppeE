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
    public void getMeThatShitFuckArray() throws IOException {
        boolean[][] map = XMLParser.parsexml("two", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("2:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }

        map = XMLParser.parsexml("three", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("3:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }

        map = XMLParser.parsexml("four", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("4:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("five", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("5:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("six", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("6:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("seven", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("7:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("eigth", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("8:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("nine", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("9:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("ten", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("10:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("eleven", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("11:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twelve", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("12:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirteen", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("13:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("fourteen", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("14:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("fifteen", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("15:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("sixteen", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("16:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("seventeen", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("17:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("eighteen", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("18:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("nineteen", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("19:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twenty", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("21:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentyone", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("21:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentytwo", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("22:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentythree", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("23:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentyfour", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("24:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentyfive", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("25:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentysix", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("26:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentyseven", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("27:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentyeigth", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("28:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("twentynine", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("29:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirty", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("30:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirtyone", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("31:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirtytwo", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("32:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirtythree", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("33:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirtyfour", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("34:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirtyfive", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("35:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
        map = XMLParser.parsexml("thirtysix", "nix",
                appContext.getAssets().open("maps.xml"));

        System.out.println("36:");
        for (boolean[] line : map) {
            System.out.print("[ ");
            for (boolean val : line)
                System.out.print(val + ", ");
            System.out.print("]");
            System.out.println();
        }
    }
}