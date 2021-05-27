package com.example.se2_gruppenphase_ss21.logic.tetris;


/**
 * A BOX is the fundamental element that a MAP consists of.
 * A BOX can be part of the play field and can also be covered by a tile.
 *
 * @author Manuel Simon #00326348
 */
public class Box extends Position {
    private Tile tile;
    // a tempTile is a Tile which does not
    private Tile tempTile;
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
     * @return true if occupied by a TILE, otherwise false;
     */
    public boolean isCoveredByTile() {
        return tile != null;
    }

    public boolean isCoveredByTempTile() {
        return tempTile != null;
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
        return tempTile;
    }

    /**
     * Sets this BOX temporary TILE.
     * @param tile the temporarily placed TILE.
     */
    public void setTempTile(Tile tile) {
        tempTile = tile;
    }
}

