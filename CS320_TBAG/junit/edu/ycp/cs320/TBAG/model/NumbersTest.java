package edu.ycp.cs320.TBAG.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.TBAG.model.Numbers;

public class NumbersTest {
	private Numbers model, model2;
	
	@Before
	public void setUp() {
		model = new Numbers(5.0, 4.3, 2.2);
		model2 = new Numbers(3.9, 7.0);
		model.setResult(8.11);
	}
	
	
	@Test
	public void testNumbers3() {
		assertEquals(5.0, model.getFirst(), 0.0);
		assertEquals(4.3, model.getSecond(), 0.0);
		assertEquals(2.2, model.getThird(), 0.0);
	}
	
	@Test
	public void testNumbers2() {
		assertEquals(3.9, model2.getFirst(), 0.0);
		assertEquals(7.0, model2.getSecond(), 0.0);
	}
	
	@Test
	public void testGetFirst() {
		assertEquals(5.0, model.getFirst(), 0);
	}
	
	@Test
	public void testGetSecond() {
		assertEquals(4.3, model.getSecond(), 0);
	}
	
	@Test
	public void testGetThird() {
		assertEquals(2.2, model.getThird(), 0);
	}
	
	@Test
	public void testGetResult() {
		assertEquals(8.11, model.getResult(), 0);
	}
	
	@Test
	public void testSetFirst() {
		model.setFirst(3.0);
		assertEquals(3.0, model.getFirst(), 0);
	}
	
	@Test
	public void testSetSecond() {
		model.setSecond(10);
		assertEquals(10, model.getSecond(), 0);
	}
	
	@Test
	public void testSetThird() {
		model.setThird(7.2);
		assertEquals(7.2, model.getThird(), 0);
	}
	
	@Test
	public void testSetResult() {
		model.setResult(20.2);
		assertEquals(20.2, model.getResult(), 0);
	}
}
