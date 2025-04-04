package edu.ycp.cs320.TBAG.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.TBAG.model.ItemWeapons;

public class ItemToolsTest {
	private ItemTools hammer;

    @Before
    public void setUp() {
        hammer = new ItemTools(6, 15, "A rusty hammer with a wooden handle", 15);
    }

    @Test
    public void testInitialization() {
        assertEquals(Integer.valueOf(6), hammer.getUses());
        assertEquals(Integer.valueOf(15), hammer.getValue());
        assertEquals("A rusty hammer with a wooden handle", hammer.getDescription());
        assertEquals(Integer.valueOf(15), hammer.getrepairProgress());
    }

    @Test
    public void testUseWeapon() {
        hammer.use();
        assertEquals(Integer.valueOf(5), hammer.getUses());
    }

    @Test
    public void testUseUntilBroken() {
        hammer.use();
        hammer.use();
        hammer.use();
        hammer.use();
        hammer.use();
        hammer.use();
        hammer.use();  // This should NOT reduce `uses` below 0

        assertEquals(Integer.valueOf(0), hammer.getUses());
    }

 }
