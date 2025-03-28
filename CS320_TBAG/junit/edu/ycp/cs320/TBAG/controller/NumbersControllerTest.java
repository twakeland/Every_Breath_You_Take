package edu.ycp.cs320.TBAG.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.TBAG.controller.NumbersController;
import edu.ycp.cs320.TBAG.model.Numbers;

public class NumbersControllerTest {
	private Numbers model;
	private NumbersController controller;
	
	@Before
	public void setUp() {
		model = new Numbers(2.3, 4.4, 6.0);
		controller = new NumbersController();
		
		controller.setModel(model);
	}
	
	@Test
	public void testAdd() {
		controller.add();
		assertEquals(12.7, model.getResult(), 0.0);
	}
	
	@Test
	public void testMultiply() {
		controller.multiply();
		assertEquals(10.12, model.getResult(), 0.0);
	}
}
