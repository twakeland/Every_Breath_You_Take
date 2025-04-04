package edu.ycp.cs320.TBAG.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.TBAG.model.ItemWeapons;

public class ItemWeaponsTest {
	private ItemWeapons axe;

    @Before
    public void setUp() {
        axe = new ItemWeapons("Axe", 2, 15, "A rusty axe", 10);
    }

    @Test
    public void testInitialization() {
        assertEquals(Integer.valueOf(2), axe.getUses());
        assertEquals(Integer.valueOf(15), axe.getValue());
        assertEquals("A rusty axe", axe.getDescription());
        assertEquals(Integer.valueOf(10), axe.getDamage());
    }

    @Test
    public void testUseWeapon() {
        axe.use();
        assertEquals(Integer.valueOf(1), axe.getUses());
    }

    @Test
    public void testUseUntilBroken() {
        axe.use();
        axe.use();
        axe.use();  // This should NOT reduce `uses` below 0

        assertEquals(Integer.valueOf(0), axe.getUses());
    }

    @Test
    public void testUsingBrokenWeapon() {
        axe.use();
        axe.use();
        axe.use();  // Weapon should already be broken

        assertEquals(Integer.valueOf(0), axe.getUses());
    }
 }
