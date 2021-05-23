package com.example.se2_gruppenphase_ss21;

import com.example.se2_gruppenphase_ss21.logic.tetris.Map;
import com.example.se2_gruppenphase_ss21.logic.tetris.Position;
import com.example.se2_gruppenphase_ss21.logic.tetris.Tile;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LogicTest {

    /**
     * visual test using Ubungo tile
     */
    @Test
    public void testMapAndTile() {
        Map map = new Map();
        boolean[][] field = {
                { false, true, true },
                {  true, true, true },
                { false, true, true },
        };

        map.setUpMap(field);

        /**
         *  XX
         *  X
         */
        Tile tile1 = new Tile(new Position(1,0), new Position(0,0), new Position(0,1));

        /**
         *  XX
         *   XX
         */
        Tile tile2 = new Tile(new Position(-1,0), new Position(0,0), new Position(0,1), new Position(1,1));

        tile1.attachToMap(map, 0,0);
        assertTrue(!tile1.isAttached());
        tile1.detachFromMap();

        tile1.attachToMap(map, 1,1);
        assertTrue(tile1.isAttached());

        tile1.detachFromMap();
        tile1.rotateLeft();
        tile1.rotateLeft();
        tile1.attachToMap(map, 1, 1);
        assertTrue(tile1.isAttached());

        tile1.detachFromMap();
        tile2.attachToMap(map, 1, 1);
        assertTrue(tile2.isAttached());

        tile2.detachFromMap();
        tile2.mirrorHorizontally();
        tile2.attachToMap(map, 1, 0);
        assertTrue(tile2.isAttached());
        assertTrue(!map.checkSolved());

        tile1.attachToMap(map, 1, 1);
        assertTrue(map.checkSolved());
    }
}
