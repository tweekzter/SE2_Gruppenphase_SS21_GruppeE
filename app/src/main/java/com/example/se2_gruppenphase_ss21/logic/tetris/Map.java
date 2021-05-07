package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.res.AssetManager;

import com.example.se2_gruppenphase_ss21.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Representation of MAP
 *
 * !! PLEASE do not make fundamental changes to the logic, without coordinating with the others !!
 *
 * A MAP represents a rectangular field and consists of
 * -> a play field (area where tiles can be placed on)
 * -> not eligible surroundings to fill up the gaps of the rectangle
 *
 * The functionality of this class includes:
 * -> setting up the play field
 * -> checking if a tile can be placed on a field
 * -> validating if riddle was solved successfully
 *
 * The indices of the map are defined as follows:
 * -> X represents horizontal axis (beginning with 0)
 * -> Y represents vertical axis (beginning with 0)
 *
 *
 * @author Manuel Simon #00326348
 */
public class Map {
    private Box[][] map;
    private int x, y;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private static ArrayList<ArrayList<ArrayList<Boolean>>> mapPool = new ArrayList<>();
    // @TODO replace this prototype map with an existing map
    private static boolean[][] standardMap = {
            { false, false, false, false },
            { false, true,  true,  false },
            { false, true,  true,  false },
            { false, true,  true,  false }
    };

    /**
     * Default constructor, creating a 6x5 map (like the modeled UBONGO version)
     */
    public Map() {
        map = new Box[6][5];
        this.x = 5;
        this.y = 6;
    }

    /**
     * Creating a custom x by y map.
     * @param x horizontal length
     * @param y vertical length
     */
    public Map(int x, int y) {
        this.x = x;
        this.y = y;
        map = new Box[y][x];
    }

    /**
     * Sets up the play field (area on which tiles are placed).
     * This is done by reading a two-dimensional boolean array, where the first array index represents the y-axis,
     * whereas the second index represents the x-axis.
     * True represents a valid field
     * False represents an invalid field
     *
     * Example:
     * If the desired play field looks like this (O field element - X invalid field element):
     * X X X X
     * X O O X
     * X O O X
     * X X X X
     *
     * this can be achieved by following array:
     * {
     *     { false, false, false, false },
     *     { false, true,  true,  false },
     *     { false, true,  true,  false },
     *     { false, false, false, false }
     * }
     *
     * @param field the setup array - true for a valid field, false for invalid
     */
    public void setUpMap(boolean[][] field) {
        if(field == null || field.length != y || field[0].length != x)
            return;

        for(int i=0; i < field.length; i++) {
            for(int j=0; j < field[i].length; j++) {
                Box box = new Box(i,j);
                box.setField(field[i][j]);
                map[i][j] = box;
            }
        }
    }

    /**
     * Covers a specific box on the map with a TILE.
     * @param tile the tile to cover box with
     * @param x x-coordinate of the box
     * @param y y-coordinate of the box
     */
    void coverBox(Tile tile, int x, int y) {
        map[y][x].setTile(tile);
    }

    /**
     * Clears coverage of a specific box on the map.
     * @param x x-coordinate of the box
     * @param y y-coordinate of the box
     */
    void clearBox(int x, int y) {
        map[y][x].setTile(null);
    }

    /**
     * Checks if a specified box on the map is available for placement.
     * @param x x-coordinate of the box
     * @param y y-coordinate of the box
     * @return true if available - false if not
     */
    boolean checkAvailable(int x, int y) {
        // coordinate outside of map
        if(y >= map.length || x >= map[0].length || y < 0 || x < 0)
            return false;

        Box box = map[y][x];
        return box.isAvailable() && box.isField();
    }

    /**
     * Add TILE to attached tiles.
     * @param tile
     */
    void addTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Checks if riddle is solved. Therefore every play field box needs to be covered by a tile.
     * @return true if riddle is solved, otherwise false.
     */
    public boolean checkSolved() {
        for(Box[] line : map) {
            for(Box box : line) {
                if(box.isField() && box.isAvailable())
                    return false;
            }
        }
        return true;
    }

    /**
     * Receive reference to the instance of Box at specified coordinates.
     * @param x x-coordinate of Box
     * @param y y-coordinate of Box
     * @return The Box at given coordinates.
     */
    public Box getBox(int x, int y) {
        return map[y][x];
    }

    public static void loadMaps(AssetManager mgr) {
        // if already loaded -> unload first
        if(mapPool.size() != 0)
            clearMapPool();

        try {
            InputStream is = mgr.open("mapPool.cfg");
            Scanner scanner = new Scanner(is);
            char[] line;
            int mapCount = 0;
            int y = 0;
            mapPool.add(new ArrayList<>());
            while(scanner.hasNext()) {
                String l = scanner.nextLine();
                if(l.length() == 0)
                    continue;
                if(l.charAt(0) == '#') {
                    mapCount++;
                    y = 0;
                    mapPool.add(new ArrayList<>());
                    continue;
                }
                mapPool.get(mapCount).add(new ArrayList<>());
                line = l.toCharArray();
                for(char c : line) {
                    if(c == 'O' || c == 'o')
                        mapPool.get(mapCount).get(y).add(true);
                    else
                        mapPool.get(mapCount).get(y).add(false);
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean[][] getMapByID(int id) {
        if(mapPool.size() == 0)
            return standardMap;

        int ySize = mapPool.get(id).size();
        int xSize = mapPool.get(id).get(0).size();
        boolean[][] map = new boolean[ySize][xSize];

        for(int y=0; y < ySize; y++) {
            for(int x=0; (x < xSize) && (x < mapPool.get(id).get(y).size()); x++) {
                map[y][x] = mapPool.get(id).get(y).get(x);
            }
        }
        return map;
    }

    /**
     * Clears the current MapPool by assigning an empty one.
     */
    public static void clearMapPool() {
        mapPool = new ArrayList<>();
    }
}

