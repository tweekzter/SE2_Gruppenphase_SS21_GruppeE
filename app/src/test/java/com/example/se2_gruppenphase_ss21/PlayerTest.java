package com.example.se2_gruppenphase_ss21;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testPlayer1(){
        Player player = new Player("1", "Player1", "2 points");
        assertEquals("1", player.getPosition());
        assertEquals("Player1", player.getName());
        assertEquals("2 points", player.getPoints());
    }

    @Test
    public void testPlayer2(){
        Player player = new Player();
        player.setPosition("2");
        player.setName("Player2");
        player.setPoints("1 points");
        assertEquals("2", player.position);
        assertEquals("Player2", player.getName());
        assertEquals("1 points", player.getPoints());
    }

    @Test
    public void testPlayer3(){
        Player player = new Player("3", "Player3", "1 points");
        assertEquals("Player{position=3, name='Player3', points=1 points}", player.toString());
    }
}
