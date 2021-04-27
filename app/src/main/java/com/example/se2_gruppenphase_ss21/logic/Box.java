package com.example.se2_gruppenphase_ss21.logic;

import android.graphics.Color;

/**
 * A BOX is the element that the MAP consists of.
 * A BOX can be part of the play field and can also be covered by a tile.
 *
 * @author Manuel Simon #00326348
 */
public class Box extends Position {
    private Tile tile;
    private boolean isField;
    private Color color;

    public Box(int x, int y) {
        super(x,y);
    }

    public Box(int x, int y, Color color) {
        super(x,y);
        this.color = color;
    }

    public void setField(boolean isField) {
        this.isField = isField;
    }

    public boolean isField() {
        return isField;
    }

    public boolean isAvailable() {
        return tile == null;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

