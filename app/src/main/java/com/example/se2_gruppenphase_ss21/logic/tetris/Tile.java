package com.example.se2_gruppenphase_ss21.logic.tetris;

import android.content.res.AssetManager;

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
    private boolean invertX = true;


    /**
     * Initializes a TILE and creates its shape with the given parameters.
     * @param pos Shape coordinates
     */
    public Tile(Position... pos) {
        for(Position p : pos)
            shape.add(p);
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
     * @return true if placed - false if not
     */
    public boolean attachToMap(Map map, Position posOnMap) {
        // absolute reference point of map
        hook = posOnMap;
        this.map = map;
        // check if tile is allowed to be placed -> return false if not
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

        this.map = null;
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

        int max = invertX ? shape.get(0).x : shape.get(0).y;
        int min = max;

        for(Position pos : shape) {
            if(invertX) {
                max = Math.max(pos.x, max);
                min = Math.min(pos.x, min);
            }
            else {
                max = Math.max(pos.y, max);
                min = Math.min(pos.y, min);
            }
        }

        invertAxis(min, max);

        invertX = !invertX;
    }

    private void invertAxis(int min, int max) {
        for(Position pos : shape) {
            if(invertX) {
                int upperDiff = max - pos.x;
                int lowerDiff = pos.x - min;
                pos.x = upperDiff < lowerDiff ? min + upperDiff : max - lowerDiff;
            }
            else {
                int upperDiff = max - pos.y;
                int lowerDiff = pos.y - min;
                pos.y = upperDiff < lowerDiff ? min + upperDiff : max - lowerDiff;
            }
        }
    }

    /**
     * Rotates the TILE counter-clockwise.
     * Has no effect if TILE is already attached to a MAP.
     */
    public void rotateLeft() {
        // just call rotateRight() with inverted axis-order
        invertX = !invertX;
        rotateRight();
        invertX = !invertX;
    }

    /**
     * Sets the absolute position of the map on which this TILE is inserted (with shape Position 0,0).
     * @param hook absolute position of map where tile is placed
     */
    void setHook(Position hook) {
        this.hook = hook;
    }

    /**
     * Get the absolute position of the MAP where tile will be placed (with shape Position 0,0).
     * @return the absolute hook position of the MAP.
     */
    Position getHook() {
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
        Position[] shape = new Position[this.shape.size()];
        this.shape.toArray(shape);
        return shape;
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
}

