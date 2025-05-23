package edu.ycp.cs320.TBAG.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import edu.ycp.cs320.TBAG.model.ItemWeapons;
import edu.ycp.cs320.TBAG.model.ItemConsumables;
import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Item;

import edu.ycp.cs320.TBAG.controller.GameEngine;

public class GameEngineTest {
	private GameEngine testEngine;
	
	@Before
	public void setUp() {
		testEngine = new GameEngine();
		testEngine.setData();
	}
	
	@Test
	public void testResponse() {
	    // Moving west should take the player to the hallway.
	    assertEquals("The hallway is long and dark. You can see a light in the distance. There is an axe leaning on the wall", testEngine.response("west"));
	}
	@Test
	public void testInvalidCommand() {
	    assertEquals("I do not recognize that command", testEngine.response("jump"));
	}

	@Test
	public void testMoveNorthFromHallway() {
	    testEngine.response("west"); // Move to hallway
	    assertEquals("The lab is filled with tons of scientific equipment you don't recognize. There is a medkit on the desk", testEngine.response("north"));
	}

	@Test
	public void testMoveInvalidDirection() {
	    assertEquals("You can't go that way", testEngine.response("down")); // No "down" exit from the start
	}
	@Test
    public void testWeaponsExistInRooms() {
        Room hallway = testEngine.getRoom(2); // Hallway
        Item axe = hallway.getInventory().getItem("A worn axe used to break down wooden barricades");

        assertNotNull(axe);
        assertTrue(axe instanceof ItemWeapons);
        assertEquals(Integer.valueOf(5), axe.getUses()); // Axe should have 5 uses
        assertEquals(Integer.valueOf(12), axe.getValue()); // Axe should have value 12
    }

    @Test
    public void testConsumablesExistInRooms() {
        Room lab = testEngine.getRoom(3); // Lab
        Item healthKit = lab.getInventory().getItem("A packet filled with single-use health stims");

        assertNotNull(healthKit);
        assertTrue(healthKit instanceof ItemConsumables);
        assertEquals(Integer.valueOf(1), healthKit.getUses()); // HealthKit should have 1 use
        assertEquals(Integer.valueOf(20), healthKit.getValue()); // HealthKit should have value 20
    }

    @Test
    public void testOxygenTankExistsInBasement() {
        Room basement = testEngine.getRoom(4); // Basement
        Item oxygenTank = basement.getInventory().getItem("A sizeable oxygen tank. Great for longer trips underwater");

        assertNotNull(oxygenTank);
        assertTrue(oxygenTank instanceof ItemConsumables);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), oxygenTank.getUses()); // OxygenTank should have infinite uses
        assertEquals(Integer.valueOf(35), oxygenTank.getValue()); // OxygenTank should have value 35
    }
}
