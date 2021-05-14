package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.res.AssetManager;

import java.util.ArrayList;

/**
 * Representation of MAP
 *
 * !! PLEASE do not make fundamental changes to the logic, without coordinating with the team !!
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
    private Box[][] boxMap;
    private ArrayList<Tile> tiles = new ArrayList<>();

    /**
     * Default constructor, creating an empty map
     */
    public Map() {
        super();
    }

    /**
     * Creates a MAP with the given shape.
     * @param field shape of the map.
     */
    public Map(boolean[][] field) {
        setUpMap(field);
    }

    /**
     * Creates a MAP with the shape of a map of the pool.
     * @param mgr AssetManager to read pool file.
     * @param id ID of map to be loaded.
     * @param category category of map (dimension, for example: "5x6")
     */
    public Map(AssetManager mgr, int id, String category) {
        boolean[][] field = StructureLoader.getStructure(mgr, id, "map", category);
        setUpMap(field);
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
        if(field == null)
            field = StructureLoader.getStandardStructure();

        int y = field.length;
        int x = field[0].length;
        boxMap = new Box[y][x];

        for(int i=0; i < field.length; i++) {
            for(int j=0; j < field[i].length; j++) {
                Box box = new Box(i,j);
                box.setField(field[i][j]);
                boxMap[i][j] = box;
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
        boxMap[y][x].setTile(tile);
    }

    /**
     * Clears coverage of a specific box on the map.
     * @param x x-coordinate of the box
     * @param y y-coordinate of the box
     */
    void clearBox(int x, int y) {
        boxMap[y][x].setTile(null);
    }

    /**
     * Checks if a specified box on the map is available for placement.
     * @param x x-coordinate of the box
     * @param y y-coordinate of the box
     * @return true if available - false if not
     */
    boolean checkAvailable(int x, int y) {
        // coordinate outside of map
        if(y >= boxMap.length || x >= boxMap[0].length || y < 0 || x < 0)
            return false;

        Box box = boxMap[y][x];
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
     * Removes the given TILE from the list of attached TILES.
     * @param tile the TILE to be removed
     * @return true if found and removed, otherwise false
     */
    boolean removeTile(Tile tile) {
        return tiles.remove(tile);
    }

    /**
     * Checks if riddle is solved. Therefore every play field box needs to be covered by a tile.
     * @return true if riddle is solved, otherwise false.
     */
    public boolean checkSolved() {
        for(Box[] line : boxMap) {
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
     * @return The Box at given coordinates or null if no Box available
     */
    public Box getBox(int x, int y) {
        if(boxMap == null || x >= boxMap.length || y >= boxMap[0].length)
            return null;
        return boxMap[y][x];
    }

    /**
     * Returns the maximum x coordinate
     * @return maximum of x
     */
    public int getXmax() {
        if(boxMap == null)
            return 0;

        return boxMap[0].length;
    }

    /**
     * Returns the maximum y coordinate
     * @return maximum of y
     */
    public int getYmax() {
        if(boxMap == null)
            return 0;

        return boxMap.length;
    }

    /**
     * Returns the a two dimensional array of Boxes representing this Maps fields.
     * @return a two dimensional array of Boxes representing this Maps fields
     */
    public Box[][] getBoxMap() {
        return boxMap;
    }

    /**
     * Sets up the this MAP with a map from the pool.
     * The pool is represented by a JSON file (structures.json in assets).
     * It contains all maps of the game.
     *
     * The category stands for the maps dimensions. Standard is "5x6".
     *
     * @param mgr AssetManager needed to read JSON file
     * @param id ID of the map
     * @param category map dimensions (for example "5x6")
     */
    public void setMapByID(AssetManager mgr, int id, String category) {
        boolean[][] field = StructureLoader.getStructure(mgr, id, "map", category);
        setUpMap(field);
    }
}

