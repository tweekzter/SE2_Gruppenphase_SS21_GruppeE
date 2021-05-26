package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

/**
 * Representation of a TILE.
 *
 * !! PLEASE do not make fundamental changes to the logic, without coordinating with the team !!
 *
 * The shape of a TILE is defined by Position data.
 * These positions are relative to a self defined reference point (0,0).
 * For example a vertical I-Element consisting of 3 blocks could look like that:
 * 0,0 - 0,1 - 0,2
 * But the same shape may also be achieved by - just locally displaced:
 * 3,1 - 3,2 - 3,3
 *
 * A TILE always needs to be associated with a map. It also needs a map-hook
 * (absolute position on the map) to be placed on the map.
 *
 * The reference point is used to place the tile on the map. This means, it is the point
 * that will be attached to the hook point of the map (absolute coordinate of the map).
 * To place a TILE centric on the hook point, it is recommended to define the shape as follows:
 * 0,-1 - 0,0 - 0,1
 *
 * Furthermore the TILE can be attached to a map (or be removed).
 * It will check if the placement is valid and attach it if so.
 * You can also check the validity of a placement manually.
 *
 *
 * @author Manuel Simon #00326348
 */
public class Tile {
    private ArrayList<Position> shape = new ArrayList<>();
    private Map map;
    private Position hook;
    private boolean isAttached = false;
    private int color = Color.RED;


    /**
     * Default constructor. Creating an empty TILE.
     */
    public Tile() {
        super();
    }
    /**
     * Initializes a TILE and creates its shape with the given parameters.
     * @param pos Shape coordinates
     */
    public Tile(Position... pos) {
        for(Position p : pos)
            shape.add(p);
    }

    /**
     * Initializes a TILE with the shape of the tile with given ID.
     * @param mgr AssetManager to load pool file
     * @param id ID of TILE in pool
     * @param category category (for example "standard")
     */
    public Tile(AssetManager mgr, int id, String category) {
        setTileByID(mgr, id, category);
    }


    /**
     * Add a point the shape.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void addPoint(int x, int y) {
        shape.add(new Position(x,y));
    }

    /**
     * Add a position the shape.
     * @param pos
     */
    public void addPoints(Position... pos) {
        for(Position p : pos)
            shape.add(p);
    }

    /**
     * Remove a position from the shape.
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if removed - false if nothing was removed
     */
    public boolean removePoint(int x, int y) {
        Position pos = new Position(x,y);
        boolean removed = shape.remove(pos);
        while(shape.remove(pos)); // in case there is more than one pos with same coordinates
        return removed;
    }

    /**
     * Description see attachToMap(Position posOnMap)
     * @param x x-coordinate of map-hook
     * @param y y-coordinate of map-hook
     * @return true if placed - false if not
     */
    public boolean attachToMap(Map map, int x, int y) {
        return attachToMap(map, new Position(x,y));
    }

    /**
     * Attaches this tile to the associated map, if possible.
     *
     * It will check if the tile collides with non-eligible parts of the map
     * or with another tile. If so, the tile will not be added, returning false.
     *
     * If the tile can be placed on the map, it will do so by occupying the maps
     * respective boxes (absolute position of the map boxes is calculated by using the hook point).
     *
     * @param posOnMap map-hook
     * @return true if successful - false if not
     */
    public boolean attachToMap(Map map, Position posOnMap) {
        if(isAttached) {
            Log.e("tile", "Tile must be detached before attaching!");
            return false;
        }
        // absolute reference point of map
        hook = posOnMap;
        this.map = map;
        // check if tile is allowed to be placed
        if(!checkPlaceable())
            return false;

        // register tile on map
        map.addTile(this);
        // place tile on the map at posOnMap
        for(int i=0; i < shape.size(); i++) {
            int x = hook.x + shape.get(i).x;
            int y = hook.y + shape.get(i).y;
            map.coverBox(this, x, y);
        }

        // tell the tile, it is attached
        isAttached = true;
        return true;
    }

    /**
     * Detaches this tile from the map.
     */
    public void detachFromMap() {
        if(hook == null || !isAttached)
            return;
        for(int i=0; i < shape.size(); i++) {
            int x = hook.x + shape.get(i).x;
            int y = hook.y + shape.get(i).y;
            map.clearBox(x, y);
        }

        map.removeTile(this);
        isAttached = false;
    }

    /**
     * Checks if tile can be placed on associated map considering this tiles map-hook.
     * @return true if it can be placed - false if not
     */
    public boolean checkPlaceable() {
        return checkPlaceable(hook);
    }

    /**
     * Checks if tile can be placed on associated map considering a custom hook point as parameter.
     * @param offset hook point
     * @return true if it can be placed - false if not
     */
    public boolean checkPlaceable(Position offset) {
        if(map == null)
            return false;

        for(Position pos : shape) {
            int x = offset.x + pos.x;
            int y = offset.y + pos.y;
            if(!map.checkAvailable(x,y))
                return false;
        }

        return true;
    }

    /**
     * Rotates the TILE clockwise.
     * Has no effect if TILE is already attached to a MAP.
     */
    public void rotateRight() {
        if(shape.size() == 0 || isAttached)
            return;

        invertY();
        switchAxis();
    }

    /**
     * Rotates the TILE counter-clockwise.
     * Has no effect if TILE is already attached to a MAP.
     */
    public void rotateLeft() {
        if(shape.size() == 0 || isAttached)
            return;

        invertX();
        switchAxis();
    }

    /**
     * Mirrors the TILE vertically
     */
    public void mirrorVertically() {
        if(shape.size() == 0 || isAttached)
            return;

        invertY();
    }

    /**
     * Mirrors the TILE horizontally
     */
    public void mirrorHorizontally() {
        if(shape.size() == 0 || isAttached)
            return;

        invertX();
    }

    /**
     * Switches the axis.
     */
    private void switchAxis() {
        for(Position pos : shape) {
            int tmp = pos.x;
            pos.x = pos.y;
            pos.y = tmp;
        }
    }

    /**
     * Inverts the x-axis coordinates of the shape Positions
     */
    private void invertX() {
        int max = shape.get(0).x;
        int min = max;
        for (Position pos : shape) {
            max = Math.max(pos.x, max);
            min = Math.min(pos.x, min);
        }

        for(Position pos : shape) {
            int upperDiff = max - pos.x;
            int lowerDiff = pos.x - min;
            pos.x = upperDiff < lowerDiff ? min + upperDiff : max - lowerDiff;
        }
    }

    /**
     * Inverts the y-axis coordinates of the shape Positions
     */
    private void invertY() {
        int max = shape.get(0).y;
        int min = max;
        for (Position pos : shape) {
            max = Math.max(pos.y, max);
            min = Math.min(pos.y, min);
        }

        for(Position pos : shape) {
            int upperDiff = max - pos.y;
            int lowerDiff = pos.y - min;
            pos.y = upperDiff < lowerDiff ? min + upperDiff : max - lowerDiff;
        }
    }

    /**
     * Sets the absolute position of the map on which this TILE is inserted (with shape Position 0,0).
     * @param hook absolute position of map where tile is placed
     */
    public void setHook(Position hook) {
        if(isAttached)
            detachFromMap();
        this.hook = hook;
    }

    /**
     * Get the absolute position of the MAP where tile will be placed (with shape Position 0,0).
     * @return the absolute hook position of the MAP.
     */
    public Position getHook() {
        return hook;
    }

    /**
     * Set the map associated with this tile.
     * This will detach the current TILE from the map of course.
     * @param map the map associated with this tile
     */
    public void setMap(Map map) {
        this.map = map;
        isAttached = false;
    }

    /**
     * Returns the map associated with this tile.
     * @return The map associated with this tile
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Returns an array of this tiles shape.
     * @return shape array
     */
    public Position[] getShape() {
        Position[] s = new Position[shape.size()];
        shape.toArray(s);
        return s;
    }

    /**
     * Indicates if tile is placed on the map.
     * @return true if placed on a map - false if not.
     */
    public boolean isAttached() {
        return isAttached;
    }

    /**
     * Sets up the TILE with a tile from the pool.
     * The pool is represented by a JSON file containing all the structures.
     *
     * @param mgr AssetManager needed to read JSON file
     * @param id ID of tile in pool
     * @param category tile category (default: "standard")
     */
    public void setTileByID(AssetManager mgr, int id, String category) {
        if(!shape.isEmpty())
            shape = new ArrayList<>();

        boolean[][] shape = StructureLoader.getStructure(mgr, id, "tile", category);
        int midY = shape.length / 2;
        int midX = shape[0].length / 2;
        for(int y=0; y < shape.length; y++) {
            for(int x=0; x < shape[y].length; x++) {
                if(shape[y][x])
                    addPoint(x-midX,y-midY);
            }
        }
    }

    /**
     * Centers a tile symmetrically around 0,0 so that it can be placed centric
     * around the insertion point on a MAP.
     */
    public void centerTile() {
        if(shape.isEmpty())
            return;

        int maxX = shape.get(0).x;
        int minX = maxX;
        int maxY = shape.get(0).y;
        int minY = maxY;
        for(Position val : shape) {
            maxX = Math.max(maxX, val.x);
            minX = Math.min(minX, val.x);
            maxY = Math.max(maxY, val.y);
            minY = Math.min(minY, val.y);
        }

        int diffX = (maxX < 0) ? (minX * -1) - (maxX * -1) : maxX - minX;
        int diffY = (maxY < 0) ? (minY * -1) - (maxY * -1) : maxY - minY;

        int offsetX = (maxX - diffX / 2) * -1;
        int offsetY = (maxY - diffY / 2) * -1;

        for(Position val : shape) {
            val.x = val.x + offsetX;
            val.y = val.y + offsetY;
        }
    }

    /**
     * Gets the color of this TILE. (Use android.graphics.Color class)
     * @return color of this TILE
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets the Color of this TILE. (use android.graphics.Color class)
     * @param color the color to set.
     */
    public void setColor(int color) {
        this.color = color;
    }
}

