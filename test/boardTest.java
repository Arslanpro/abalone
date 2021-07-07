package abalone.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import abalone.element.Board;
import abalone.element.Field;
import abalone.protocol.enums.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import abalone.protocol.enums.Direction;

public class boardTest {
	public Board board;
	
	@BeforeEach
	public void setUp() {
		board = new Board();
		
	}
	@Test
	public void testinitPlayerFields() {
		board.initPlayerFields();
		assertTrue(board.fields[1][1].isEmpty());
		assertTrue(board.fields[1][2].isEmpty());
		assertTrue(board.fields[1][3].isEmpty());
		assertTrue(board.fields[1][4].isEmpty());
		board.reset();
		board.setPlayerNumber(3);
		board.initPlayerFields();
		
		assertTrue(board.fields[1][1].isEmpty());
		assertTrue(board.fields[1][2].isEmpty());
		assertTrue(board.fields[1][3].isEmpty());
		assertTrue(board.fields[1][4].isEmpty());
		board.reset();
		board.setPlayerNumber(4);
		assertTrue(board.fields[1][1].isEmpty());
		assertTrue(board.fields[1][2].isEmpty());
		assertTrue(board.fields[1][3].isEmpty());
		assertTrue(board.fields[1][4].isEmpty());
	}
	@Test
	public void testisValidMove() {
		assertFalse(board.isValidMove(board.fields[2][3], board.fields[2][2],Direction.STRAIGHT_LEFT , Color.BLACK));
		
	}
	
	
	
	@Test
	public void testgetField() {
		assertEquals(board.fields[1][1],board.getFeild(1, 1));
	}
	@Test 
	public void testfindFieldbyPosition() {
		assertEquals(board.fields[1][1],board.findFieldByPosition('a', 1));
	}
	@Test
	public void testgetWinner() {
		board.deadBlackCount = 7;
		assertTrue(board.getWinner().contains(Color.BLUE));
	}
	@Test
	public void testisOutofboard() {
		Field f1 = new Field();
		f1.setX(15);
		f1.setY(3);
		assertTrue(board.isOutOfBoard(f1));
	}
	@Test
	public void isValidMovetest() {
		assertFalse(board.isValidMove("B5", "C2", Direction.DOWN_LEFT, Color.BLACK));
		assertFalse(board.isValidMove("B5", "C7", Direction.DOWN_LEFT, Color.BLACK));
		assertFalse(board.isValidMove("B5", "H7", Direction.DOWN_RIGHT, Color.BLACK));
		assertFalse(board.isValidMove("A9", "B9", Direction.DOWN_RIGHT, Color.WHITE));
		
	}
	@Test
	public void copytest() {
		Board copyb1 = board.deepCopy();
		assertTrue(copyb1.getFeild(1, 1).isSamePosition(board.fields[1][1]));
	}
	@Test
	public void setinitbyReftest() {
		int[][] a1= 
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0},
                    {0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0},
                    {0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
		board.setInitialFeildsByReference(a1);
		assertTrue(board.fields[2][6].getMarble().getColor()==Color.BLACK);
	}
	
}
