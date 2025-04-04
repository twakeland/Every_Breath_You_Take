package edu.ycp.cs320.TBAG.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.TBAG.model.ItemConsumables;

public class ItemConsumablesTest {
	private ItemConsumables healthKit;

    @Before
    public void setUp() {
        healthKit = new ItemConsumables("Health Kit", 3, 20, "A small medkit", 50);
    }

    @Test
    public void testInitialization() {
        assertEquals(Integer.valueOf(3), healthKit.getUses());
        assertEquals(Integer.valueOf(20), healthKit.getValue());
        assertEquals("A small medkit", healthKit.getDescription());
        assertEquals(Integer.valueOf(50), healthKit.getHealingAmount());
    }

    @Test
    public void testUseConsumable() {
        healthKit.use();
        assertEquals(Integer.valueOf(2), healthKit.getUses());
    }

    @Test
    public void testUseUntilEmpty() {
        healthKit.use();
        healthKit.use();
        healthKit.use();
        healthKit.use();  // Shouldn't go below zero

        assertEquals(Integer.valueOf(0), healthKit.getUses());
    }
}
