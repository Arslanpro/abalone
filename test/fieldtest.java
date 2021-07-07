package abalone.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import abalone.element.Field;
import abalone.element.Marble;
import org.junit.jupiter.api.Test;

import abalone.protocol.enums.Color;

import org.junit.jupiter.api.BeforeEach;

public class fieldtest {
	public Field field, field2;
	@BeforeEach
	public void setUp() {
		field = new Field();
		field.setX(2);
		field.setY(2);
	
		
	}
	@Test
	public void testisSamePosition() {
		field2 = new Field();
		field2.x = 2;
		field.y = 2;
		
		assertFalse(field.isSamePosition(field2));
	}
	@Test
	public void testisEmpty() {
		assertTrue(field.isEmpty());
	}
	@Test
	public void testreset() {
		field.getMarble().setColor(Color.BLACK);
		field.reset();
		assertEquals(Color.NONE,field.getMarbleColor());
	}
	@Test
	public void testgetMarbleColor() {
		assertEquals(Color.NONE,field.getMarbleColor());
	}
	@Test
	public void testgetX() {
		assertEquals(2,field.getX());
	}
	@Test
	public void testgetY() {
		assertEquals(2,field.getY());
	}
	@Test
	public void testSetX() {
		field.setX(3);
		assertEquals(3,field.getX());
	}
	@Test
	public void testSetY() {
		field.setY(3);
		assertEquals(3,field.getY());
	}
	@Test
	public void testgetMarble() {
		assertEquals(field.marble,field.getMarble());
	}
	@Test
	public void testSetMarble() {
		Marble m2 = new Marble();
		field.setMarble(m2);
		assertEquals(m2,field.getMarble());
	}
	
}
