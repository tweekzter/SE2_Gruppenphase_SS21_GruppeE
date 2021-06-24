package com.example.se2_gruppenphase_ss21.logic.tetris;

import org.junit.Test;
import static org.junit.Assert.*;


public class MapAndTileTests {
    private static final boolean[][] standardStructure = {
            { false, false, false, false },
            { false, true,  true,  false },
            { false, true,  true,  false },
            { false, false, false, false }
    };

    /**
     * visual test
     */
    @Test
    public void testMapAndTile() {
        Map map = new Map();

        boolean[][] field = {
                {false, true,  true,  false, false},
                {false, true,  true,  true,  false},
                {false, true,  true,  true,  false},
                {false, true,  true,  true,  false},
                {false, false, false, true,  false},
                {false, false, false, true,  false},

        };
        /*
        boolean[][] field = {
                { true, true, true, true, true },
                { true, true, true, true, true },
                { true, true, true, true, true },
                { true, true, true, true, true },
                { true, true, true, true, true },
                { true, true, true, true, true },

        };
         */

        map.setUpMap(field);

        Position p1 = new Position(1, 0);
        Position p2 = new Position(0, 0);
        Position p3 = new Position(0, 1);
        Position p4 = new Position(0, 2);
        Tile tile = new Tile(p1, p2, p3, p4);
        Tile tile2 = new Tile(p2, new Position(1, 0));
        Tile tile3 = new Tile(new Position(2, 0), new Position(2, 2), new Position(3, 2),
                new Position(2, 3), new Position(3, 3), new Position(3, 4), new Position(3, 5));


        tile.attachToMap(map, 1, 1);
        //tile2.attachToMap(2,1);
        //tile3.attachToMap(0,0);

        displayMap(map);
        System.out.println("*************");
        tile.detachFromMap();
        tile.rotateRight();
        tile.attachToMap(map, 1, 1);
        displayMap(map);
        System.out.println("*************");
        tile.detachFromMap();
        tile.rotateRight();
        tile.attachToMap(map, 1, 1);
        displayMap(map);
        System.out.println("*************");
        tile.detachFromMap();
        tile.rotateRight();
        tile.attachToMap(map, 1, 1);
        displayMap(map);
        System.out.println("*************");
        tile.detachFromMap();
        tile.rotateRight();
        tile.attachToMap(map, 1, 1);
        displayMap(map);
        System.out.println(map.checkSolved());
        tile.detachFromMap();
        tile.rotateLeft();
        tile.attachToMap(map, 1, 1);
        displayMap(map);
        System.out.println(map.checkSolved());
        tile.detachFromMap();
        tile.rotateRight();
        tile.attachToMap(map, 1, 1);
        displayMap(map);
        System.out.println(map.checkSolved());
        tile.detachFromMap();
        tile.mirrorVertically();
        tile.attachToMap(map, 1, 1);
        displayMap(map);
        System.out.println(map.checkSolved());
        tile.detachFromMap();
        tile.mirrorHorizontally();
        System.out.println("first attachment: "+tile.attachToMap(map, 1, 1));
        displayMap(map);
        System.out.println(map.checkSolved());

        Tile collide = new Tile(new Position(0,0),
                new Position(1,0));

        System.out.println("Does this work? "+collide.attachToMap(map, new Position(1,0)));
        displayMap(map);


        Tile temp = new Tile();
        temp.addPoint(-1,0);
        temp.addPoint(0,0);
        temp.addPoint(1,0);
        System.out.println(temp.placeTempOnMap(map, new Position(3,3)));
        displayMap(map);
        Tile temp2 = new Tile();
        temp2.addPoint(0,0);
        temp2.addPoint(0,1);
        System.out.println(temp2.placeTempOnMap(map, new Position(4,2)));
        displayMap(map);
        temp2.removeTempFromMap();
        System.out.println("****");
        displayMap(map);

        for (Position pos : tile.getShape()) {
            System.out.print(pos.x + ", ");
            System.out.print(pos.y + "\n");
        }
    }

    private static void displayMap(Map map) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                if (map.getBox(x, y).isField() && map.getBox(x, y).getTile() != null)
                    System.out.print("I ");
                else if(map.getBox(x, y).isCoveredByTempTile())
                    System.out.print("* ");
                else if (map.getBox(x, y).isField())
                    System.out.print("O ");
                else
                    System.out.print("X ");
            }
            System.out.println();
        }
    }

    @Test
    public void testToMatrix() {
        Tile tile = new Tile();
        tile.addPoint(-2,0);
        tile.addPoint(-1,0);
        tile.addPoint(0,1);

        boolean[][] expected = {
                { true, true, false },
                { false, false, true }
        };
        boolean[][] actual = tile.getShapeMatrix();
        for(boolean[] row : actual) {
            for(boolean val : row)
                System.out.print(val+", ");
            System.out.println();
        }
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testAddPointAndGetShape() {
        Tile tile = new Tile();
        Position[] expected = { new Position(2,5), new Position(8,2) };

        tile.addPoint(2,5);
        tile.addPoint(8,2);
        assertArrayEquals(expected, tile.getShape());

        tile = new Tile();
        tile.addPoints(new Position(2,5), new Position(8,2));
        assertArrayEquals(expected, tile.getShape());
    }

    @Test
    public void removePoint() {
        Tile tile = new Tile();
        Position[] expected = { new Position(8,2), new Position(10,1) };

        tile.addPoint(2,5);
        tile.addPoint(8,2);
        tile.addPoint(10,1);
        tile.removePoint(2,5);

        assertArrayEquals(expected, tile.getShape());
        assertTrue(tile.removePoint(8,2));
        assertTrue(tile.removePoint(10,1));
        assertEquals(0, tile.getShape().length);
    }

    @Test
    public void testAttachToMapValid() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertTrue(tile.attachToMap(map, 1,1));
        assertEquals(tile, map.getBox(1,1).getTile());
        assertEquals(tile, map.getBox(2,1).getTile());
        assertTrue(tile.isAttached());
    }

    @Test
    public void testAttachToMapBlocked() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertFalse(tile.attachToMap(map, 2,1));
        assertNotEquals(tile, map.getBox(2,1).getTile());
        assertNotEquals(tile, map.getBox(3,1).getTile());
        assertFalse(tile.isAttached());
    }

    @Test
    public void testAttachToMapOutOfBounds() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertFalse(tile.attachToMap(map, 3,1));
        assertNotEquals(tile, map.getBox(3,1).getTile());
    }

    @Test
    public void testAttachToMapOutOfBoundsWithNegativeHook() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertFalse(tile.attachToMap(map, -1,1));
        assertNotEquals(tile, map.getBox(0,1).getTile());
    }

    @Test
    public void testPlaceTempOnMapWithNegativeHook() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertTrue(tile.placeTempOnMap(map, new Position(-1,1)));
        assertEquals(tile, map.getBox(0,1).getTempTile());

    }

    @Test
    public void testPlaceOnMapBlocked() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertTrue(tile.placeTempOnMap(map, new Position(0,1)));
        assertEquals(tile, map.getBox(0,1).getTempTile());
        assertEquals(tile, map.getBox(1,1).getTempTile());
    }

    @Test
    public void testPlaceOnMapWithinPlayField() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertTrue(tile.placeTempOnMap(map, new Position(1,1)));
        assertEquals(tile, map.getBox(1,1).getTempTile());
        assertEquals(tile, map.getBox(2,1).getTempTile());
        assertTrue(map.getBox(1,1).isCoveredByTempTile());
        assertTrue(map.getBox(2,1).isCoveredByTempTile());

        assertTrue(tile.placeTempOnMap(map, new Position(0,0)));
        assertTrue(map.getBox(0,0).isCoveredByTempTile());
        assertTrue(map.getBox(1,0).isCoveredByTempTile());
        assertFalse(map.getBox(2,1).isCoveredByTempTile());

    }

    @Test
    public void testNoTileAttachedOrPlacedInBox() {

        Map map = new Map(standardStructure);

        assertFalse(map.getBox(0,0).isCoveredByTile());
        assertFalse(map.getBox(0,0).isCoveredByTempTile());
    }

    @Test
    public void testDetachFromMapValid() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertFalse(tile.detachFromMap());
        tile.placeTempOnMap(map, new Position(1,1));
        assertFalse(tile.detachFromMap());

        assertTrue(tile.attachToMap(map, 1,1));
        assertTrue(tile.detachFromMap());
        assertNotEquals(tile, map.getBox(1,1).getTile());
        assertFalse(map.getBox(1,1).isCoveredByTile());

    }

    @Test
    public void testDetachFromMapInvalid() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertTrue(tile.placeTempOnMap(map, new Position(1,1)));
        assertFalse(tile.detachFromMap());
        assertNotEquals(tile, map.getBox(1,1).getTile());
        assertFalse(map.getBox(1,1).isCoveredByTile());

    }

    @Test
    public void testPlaceOnMapAndRemove() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,0));

        assertTrue(tile.placeTempOnMap(map, new Position(1,1)));
        assertTrue(tile.removeTempFromMap());
        assertNotEquals(tile, map.getBox(1,1).getTempTile());
        assertNotEquals(tile, map.getBox(2,1).getTempTile());
    }

    @Test
    public void testCollidesWithBorderTrue() {

        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,1));
        assertTrue(tile.collidesWithMapBorder(new Position(0,0)));
        tile.setMap(map);

        assertTrue(tile.collidesWithMapBorder(new Position(-1,1)));
        assertTrue(tile.collidesWithMapBorder(new Position(3,1)));
        assertTrue(tile.collidesWithMapBorder(new Position(1,-1)));
        assertTrue(tile.collidesWithMapBorder(new Position(1,3)));
    }

    @Test
    public void testRotateRight() {
        Tile tile = new Tile(new Position(-1,0), new Position(0,0),
                new Position(1,0), new Position(1,1));
        Position[] expected = { new Position(1,-1), new Position(1,0),
                new Position(1,1), new Position(0,1) };

        tile.rotateRight();
        assertArrayEquals(expected, tile.getShape());

        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.rotateRight();
        Position[] expected2 = { new Position(0,0), new Position(0,1) };
        assertArrayEquals(expected2, tile2.getShape());
    }

    @Test
    public void testRotateLeft() {
        Tile tile = new Tile(new Position(1,-1), new Position(1,0),
                new Position(1,1), new Position(0,1));
        Position[] expected = { new Position(-1,0), new Position(0,0),
                new Position(1,0), new Position(1,1) };

        tile.rotateLeft();
        assertArrayEquals(expected, tile.getShape());

        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.rotateLeft();
        Position[] expected2 = { new Position(0,0), new Position(0,1) };
        assertArrayEquals(expected2, tile2.getShape());
    }

    @Test
    public void testMirrorHorizontally() {
        Tile tile = new Tile(new Position(-1,0), new Position(0,0),
                new Position(1,0), new Position(1,1));
        Position[] expected = { new Position(1,0), new Position(0,0),
                new Position(-1,0), new Position(-1,1) };

        tile.mirrorHorizontally();
        assertArrayEquals(expected, tile.getShape());

        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.mirrorHorizontally();
        Position[] expected2 = { new Position(0,0), new Position(0,1) };
        assertArrayEquals(expected2, tile2.getShape());
        tile2.detachFromMap();
    }

    @Test
    public void testMirrorVertically() {
        Tile tile = new Tile(new Position(-1,0), new Position(0,0),
                new Position(1,0), new Position(1,1));
        Position[] expected = { new Position(-1,1), new Position(0,1),
                new Position(1,1), new Position(1,0) };

        tile.mirrorVertically();
        assertArrayEquals(expected, tile.getShape());

        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.mirrorVertically();
        Position[] expected2 = { new Position(0,0), new Position(0,1) };
        assertArrayEquals(expected2, tile2.getShape());
        tile2.detachFromMap();
    }

    @Test
    public void testCenterTile() {
        Tile tile = new Tile();
        tile.addPoint(0,0);
        tile.addPoint(1,1);
        tile.addPoint(2,2);

        Position[] expected = { new Position(-1,-1), new Position(0,0), new Position(1,1) };
        tile.centerTile();

        assertArrayEquals(expected, tile.getShape());
    }

    @Test
    public void testSetUpMap() {

        Map map = new Map(standardStructure);

        boolean[][] actual = new boolean[map.getSizeY()][map.getSizeX()];
        for(int y=0; y < map.getSizeY(); y++) {
            for(int x=0; x < map.getSizeX(); x++) {
                if(map.getBox(x,y).isField())
                    actual[y][x] = true;
            }
        }

        assertArrayEquals(standardStructure, actual);
    }

    @Test
    public void testCheckSolved() {

        Map map = new Map(standardStructure);

        assertFalse(map.checkSolved());

        Tile tile1 = new Tile(new Position(0,0), new Position(1,0));
        Tile tile2 = new Tile(new Position(0,0), new Position(1,0));
        tile1.attachToMap(map, 1,1);
        assertFalse(map.checkSolved());
        tile2.attachToMap(map,2,2);
        assertFalse(map.checkSolved());
        tile2.detachFromMap();
        tile2.attachToMap(map,1,2);
        assertTrue(map.checkSolved());
    }

    @Test
    public void testTempTileOverlappingAndRemoving() {

        Map map = new Map(standardStructure);

        Tile tile1 = new Tile(new Position(1,1));
        Tile tile2 = new Tile(new Position(1,1));
        Tile tile3 = new Tile(new Position(1,1));

        tile1.placeTempOnMap(map, new Position(0,0));
        tile2.placeTempOnMap(map, new Position(0,0));
        tile3.placeTempOnMap(map, new Position(0,0));

        assertEquals(tile3, map.getBox(1,1).getTempTile());
        assertTrue(tile1.removeTempFromMap());
        assertEquals(tile3, map.getBox(1,1).getTempTile());
        assertTrue(tile3.removeTempFromMap());
        assertEquals(tile2, map.getBox(1,1).getTempTile());
        assertTrue(tile2.removeTempFromMap());
        assertFalse(tile1.removeTempFromMap());
        assertNull(map.getBox(1,1).getTempTile());
    }

    @Test
    public void testPlaceableNull() {
        Map map = new Map(standardStructure);
        Tile tile = new Tile(new Position(0,0), new Position(1,1));
        tile.setMap(map);
        assertFalse(tile.checkPlaceable(new Position(-1,0)));
        assertFalse(tile.checkPlaceable(new Position(3,0)));
        assertFalse(tile.checkPlaceable(new Position(0,-1)));
        assertFalse(tile.checkPlaceable(new Position(0,3)));
        tile.setMap(null);
        assertFalse(tile.checkPlaceable(new Position(-1,0)));
    }

    @Test
    public void testRotateRightAndPlace() {
        Tile tile = new Tile(new Position(-1,0), new Position(0,0),
                new Position(1,0), new Position(1,1));

        Map map = new Map(standardStructure);
        tile.placeTempOnMap(map, new Position(1,1));
        tile.rotateRightAndPlace(map);
        assertTrue(map.getBox(2,0).isCoveredByTempTile());
        assertTrue(map.getBox(2,1).isCoveredByTempTile());
        assertTrue(map.getBox(2,2).isCoveredByTempTile());
        assertTrue(map.getBox(1,2).isCoveredByTempTile());


        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.rotateRight();
    }

    @Test
    public void testRotateLeftAndPlace() {
        Tile tile = new Tile(new Position(1,-1), new Position(1,0),
                new Position(1,1), new Position(0,1));

        Map map = new Map(standardStructure);
        tile.placeTempOnMap(map, new Position(1,1));
        tile.rotateLeftAndPlace(map);
        assertTrue(map.getBox(0,1).isCoveredByTempTile());
        assertTrue(map.getBox(1,1).isCoveredByTempTile());
        assertTrue(map.getBox(2,1).isCoveredByTempTile());
        assertTrue(map.getBox(2,2).isCoveredByTempTile());


        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.rotateLeft();
        Position[] expected2 = { new Position(0,0), new Position(0,1) };
        assertArrayEquals(expected2, tile2.getShape());
    }

    @Test
    public void mirrorHorizontallyAndPlace() {
        Tile tile = new Tile(new Position(-1,0), new Position(0,0),
                new Position(1,0), new Position(1,1));

        Map map = new Map(standardStructure);
        tile.placeTempOnMap(map, new Position(1,1));
        tile.mirrorHorizontallyAndPlace(map);
        assertTrue(map.getBox(2,1).isCoveredByTempTile());
        assertTrue(map.getBox(1,1).isCoveredByTempTile());
        assertTrue(map.getBox(0,1).isCoveredByTempTile());
        assertTrue(map.getBox(0,2).isCoveredByTempTile());


        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.rotateLeft();
        Position[] expected2 = { new Position(0,0), new Position(0,1) };
        assertArrayEquals(expected2, tile2.getShape());
    }

    @Test
    public void mirrorVerticallyAndPlace() {
        Tile tile = new Tile(new Position(-1,0), new Position(0,0),
                new Position(1,0), new Position(1,1));

        Map map = new Map(standardStructure);
        tile.placeTempOnMap(map, new Position(1,1));
        tile.mirrorVerticallyAndPlace(map);
        assertTrue(map.getBox(0,2).isCoveredByTempTile());
        assertTrue(map.getBox(1,2).isCoveredByTempTile());
        assertTrue(map.getBox(2,2).isCoveredByTempTile());
        assertTrue(map.getBox(2,1).isCoveredByTempTile());


        Tile tile2 = new Tile(new Position(0,0), new Position(0,1));
        tile2.attachToMap(new Map(standardStructure), 1,1);
        tile.rotateLeft();
        Position[] expected2 = { new Position(0,0), new Position(0,1) };
        assertArrayEquals(expected2, tile2.getShape());
    }

    @Test
    public void testGetTilesOnMap() {
        Map map = new Map(standardStructure);
        Tile t1 = new Tile(new Position(0,0));
        Tile t2 = new Tile(new Position(0,1));
        Tile t3 = new Tile(new Position(1,0));
        t1.attachToMap(map, 1,1);
        t2.attachToMap(map, 1,1);
        t3.attachToMap(map, 1,1);
        Tile[] expected = { t1, t2, t3 };
        assertArrayEquals(expected, map.getTile());
    }

    @Test
    public void testSetHook() {
        Map map = new Map(standardStructure);
        Tile t1 = new Tile(new Position(0,0));
        Position pos = new Position(1,1);
        t1.setHook(pos);
        assertEquals(pos, t1.getHook());
        t1.attachToMap();
        Position pos2 = new Position(1,1);
        t1.setHook(pos2);
        assertFalse(t1.isAttached());
        assertEquals(pos2, t1.getHook());
    }
}
