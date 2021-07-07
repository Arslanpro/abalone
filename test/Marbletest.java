package abalone.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import abalone.protocol.enums.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import abalone.protocol.enums.Direction;
import abalone.client.AbaloneClientTUI;
import abalone.element.*;

import abalone.protocol.enums.Color;


public class Marbletest {
	Marble m1,m2,m3,m4;
	
	@BeforeEach
	public void setUp() {
		 m1 = new Marble();
		 m2 = new Marble();
		 m3 = new Marble();
		 m4 = new Marble();
		 m1.setColor(Color.BLACK);
		 m2.setColor(Color.BLUE);
		 m3.setColor(Color.RED);
		 m4.setColor(Color.WHITE);
		 
	}
	@Test
	public void isFriendtest() {
		assertTrue(m1.isFriend(m3));
		assertTrue(m2.isFriend(m4));
		assertTrue(m3.isFriend(m1));
		assertTrue(m4.isFriend(m2));
		
		
	}
	@Test
	public void testenmies() {
		m1.enemies();
		assertTrue(m1.enemies().contains(Color.WHITE));
		assertTrue(m1.enemies().contains(Color.BLUE));
		m2.enemies();
		assertTrue(m2.enemies().contains(Color.BLACK));
		assertTrue(m2.enemies().contains(Color.RED));
		m3.enemies();
		assertTrue(m3.enemies().contains(Color.WHITE));
		assertTrue(m3.enemies().contains(Color.BLUE));
		m4.enemies();
		assertTrue(m4.enemies().contains(Color.BLACK));
		assertTrue(m2.enemies().contains(Color.RED));
	}
	@Test
	public void testgetColor() {
		m1.getColor();
		assertTrue(m1.getColor() == Color.BLACK);
		m2.getColor();
		assertTrue(m2.getColor() == Color.BLUE);
		m3.getColor();
		assertTrue(m3.getColor() == Color.RED);
		m4.getColor();
		assertTrue(m4.getColor() == Color.WHITE);
	}
	@Test
	public void testsetColor() {
		m1.setColor(Color.NONE);
		m2.setColor(Color.NONE);
		m3.setColor(Color.NONE);
		m4.setColor(Color.NONE);
		assertTrue(m1.getColor() == Color.NONE);
		assertTrue(m2.getColor() == Color.NONE);
		assertTrue(m3.getColor() == Color.NONE);
		assertTrue(m4.getColor() == Color.NONE);
	}

}
