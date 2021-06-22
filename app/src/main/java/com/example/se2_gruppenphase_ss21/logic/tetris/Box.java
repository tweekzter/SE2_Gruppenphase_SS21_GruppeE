package com.example.se2_gruppenphase_ss21.logic.tetris;


import java.util.LinkedList;

/**
 * A BOX is the fundamental element that a MAP consists of.
 * A BOX can be part of the play field and be covered by an attached or an temporary placed TILE.
 *
 * @author Manuel Simon #00326348
 */
public class Box extends Position {
    private Tile tile;
    // a tempTile is a Tile which is not yet attached to the MAP
    private LinkedList<Tile> tempTiles = new LinkedList<>();
    private boolean isField;

    public Box(int x, int y) {
        super(x,y);
    }

    /**
     * Define this BOX as part of the play-field or a part of the surrounding.
     * @param isField if part of play-field true, otherwise false
     */
    public void setField(boolean isField) {
        this.isField = isField;
    }

    /**
     * Returns if this BOX is part of the play-field.
     * @return true if part of the play-field - otherwise false.
     */
    public boolean isField() {
        return isField;
    }

    /**
     * Indicates if this BOX is available to place a TILE on -
     * in other words - not occupied by a TILE already and part of the play field.
     *
     * @return if BOX is available true, otherwise false.
     */
    public boolean isAvailable() {
        return tile == null && isField;
    }

    /**
     * Returns if this BOX is occupied by a TILE.
     * @return true if occupied by a TILE, otherwise false.
     */
    public boolean isCoveredByTile() {
        return tile != null;
    }

    /**
     * Returns if this BOX is covered by one or more temporarily placed TILEs.
     * @return true if covered by a temp TILE, otherwise false.
     */
    public boolean isCoveredByTempTile() {
        return !tempTiles.isEmpty();
    }

    /**
     * Returns the TILE attached to this BOX.
     * @return the TILE attached to this BOX.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Sets the TILE attached to this BOX.
     * @param tile TILE attached to this BOX.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Returns the temporary TILE of this BOX.
     * @return the temporarily placed TILE.
     */
    public Tile getTempTile() {
        return tempTiles.peek();
    }

    /**
     * Sets this BOX temporary TILE.
     * @param tile the temporarily placed TILE.
     */
    public void setTempTile(Tile tile) {
        tempTiles.push(tile);
    }

    /**
     * Removes the specified TILE from this BOXs temporary TILEs.
     * @param tile the TILE to delete
     * @return true if found and deleted, otherwise false.
     */
    public boolean removeTempTile(Tile tile) {
        return tempTiles.remove(tile);
    }

    /**
     * Checks if two BOXes share the same attribute values.
     * This could be useful, if you design a second layer of boxes.
     * @param o Box to compare with
     * @return true if attributes match, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Box))
            return false;

        Box compare = (Box) o;

        return compare.x == x && compare.y == y
                && compare.tile == tile && tempTiles.equals(compare.tempTiles);
    }

    /**
     * Produces some hashcode based on its superclass and attributes.
     * @return the same value if objects are equal.
     */
    @Override
    public int hashCode() {
        int hashTemps = 0;
        for(Tile t : tempTiles)
            hashTemps += t.hashCode();

        return super.hashCode() + tile.hashCode() + hashTemps;
    }
}