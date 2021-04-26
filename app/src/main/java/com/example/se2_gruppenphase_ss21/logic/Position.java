package com.example.se2_gruppenphase_ss21.logic;

/**
 * A POSITION is the most basic element.
 *
 * It represents the coordinates of an element of the MAP-RIDDLE.
 *
 * @author Manuel Simon #00326348
 */
public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Position))
            return false;

        Position pos = (Position) o;
        return (pos.x == this.x) && (pos.y == this.y);
    }
}

