package com.example.se2_gruppenphase_ss21;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testPlayer1(){
        Player player = new Player("1", "Bob", "2 points");
        assertEquals("1", player.getPosition());
        assertEquals("Bob", player.getName());
        assertEquals("2 points", player.getPoints());
    }
}
