package com.example.se2_gruppenphase_ss21.logic.tetris;

/**
 * A POSITION is the most basic element.
 *
 * It represents the coordinates of an element of the MAP-RIDDLE.
 *
 * @author Manuel Simon #00326348
 */
public class Position {
    protected int x;
    protected int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compares two POSITIONS with each other.
     * @param o POSITION to compare with
     * @return true of x and y are the same, otherwise false
     */
    public boolean equals(Object o) {
        if(!(o instanceof Position))
            return false;

        Position pos = (Position) o;
        return (pos.x == this.x) && (pos.y == this.y);
    }

    /**
     * A simple hashcode algorithm that returns the same value if the two POSITIONs equal.
     * The values are not unique per differing POSITION, but good enough.
     * @return a hashcode for POSITION
     */
    public int hashCode() {
        return x*31 + y;
    }

    /**
     * Returns the x-coordinate of this POSITION.
     * @return x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this POSITION.
     * @return y-coordinate
     */
    public int getY() {
        return y;
    }
}

