package edu.ycp.cs320.TBAG.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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
		assertEquals(testEngine.response("west"), "Welcome to the starting area");
	}
	
}
