package com.kodova.carpenter.progression;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerMovableTest {

	@Test
	public void shouldStepByOne() throws Exception {
		IntegerMovable movable = new IntegerMovable(1, 0);
		assertEquals(0, (int) movable.next());
		assertEquals(1, (int) movable.next());
		assertEquals(2, (int) movable.next());
		assertEquals(3, (int) movable.next());
		assertEquals(4, (int) movable.next());
	}

	@Test
	public void shouldStartAtTen() throws Exception {
		IntegerMovable movable = new IntegerMovable(1, 10);
		assertEquals(10, (int) movable.next());
	}

	@Test
	public void shouldShouldStepBy3() throws Exception {
		IntegerMovable movable = new IntegerMovable(3, 0);
		assertEquals(0, (int) movable.next());
		assertEquals(3, (int) movable.next());
		assertEquals(6, (int) movable.next());
		assertEquals(9, (int) movable.next());
	}

	@Test
	public void shouldStepDownByOne() throws Exception {
		IntegerMovable movable = new IntegerMovable(-1, 0);
		assertEquals(0, (int) movable.next());
		assertEquals(-1, (int) movable.next());
		assertEquals(-2, (int) movable.next());
		assertEquals(-3, (int) movable.next());
		assertEquals(-4, (int) movable.next());
	}

}
