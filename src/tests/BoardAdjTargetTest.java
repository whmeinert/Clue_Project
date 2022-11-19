package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(23, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(21, 5)));
		assertTrue(testList.contains(board.getCell(3, 22)));
		
		// now test the ballroom (note not marked since multiple test here)
		testList = board.getAdjList(4, 12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(9, 12)));
		
		// one more room, the kitchen
		testList = board.getAdjList(22, 20);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(19, 18)));
		assertTrue(testList.contains(board.getCell(2, 3)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(9, 12);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 11)));
		assertTrue(testList.contains(board.getCell(9, 13)));
		assertTrue(testList.contains(board.getCell(4, 12)));

		testList = board.getAdjList(6, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 18)));
		assertTrue(testList.contains(board.getCell(7, 19)));
		assertTrue(testList.contains(board.getCell(3, 22)));
	
		testList = board.getAdjList(13, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(12, 7)));
		assertTrue(testList.contains(board.getCell(13, 8)));
		assertTrue(testList.contains(board.getCell(14, 7)));
		assertTrue(testList.contains(board.getCell(13, 3)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(26, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(25, 16)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(9, 14);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(9, 13)));
		assertTrue(testList.contains(board.getCell(9, 15)));

		// Test adjacent to walkways
		testList = board.getAdjList(19, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(19, 5)));
		assertTrue(testList.contains(board.getCell(19, 7)));
		assertTrue(testList.contains(board.getCell(18, 6)));
		assertTrue(testList.contains(board.getCell(20, 6)));

		// Test next to closet
		testList = board.getAdjList(1,16);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 17)));
		assertTrue(testList.contains(board.getCell(2, 16)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInGarage() {
		// test a roll of 1
		board.calcTargets(board.getCell(14, 20), 1, true);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(10, 18)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(14, 20), 3, true);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(9, 17)));
		assertTrue(targets.contains(board.getCell(8, 18)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(14, 20), 4, true);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(7, 18)));
		assertTrue(targets.contains(board.getCell(8, 17)));	
	}
	
	@Test
	public void testTargetsInOffice() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 3), 1, true);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(22, 20)));
		assertTrue(targets.contains(board.getCell(6, 6)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(2, 3), 3, true);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(6, 4)));
		assertTrue(targets.contains(board.getCell(7, 5)));	
		assertTrue(targets.contains(board.getCell(8, 6)));
		assertTrue(targets.contains(board.getCell(22, 20)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(2, 3), 4, true);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(6, 3)));
		assertTrue(targets.contains(board.getCell(7, 4)));	
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(22, 20)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(10, 18), 1, true);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(14, 20)));
		assertTrue(targets.contains(board.getCell(10, 19)));	
		assertTrue(targets.contains(board.getCell(10, 17)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(10, 18), 3, true);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(14, 20)));
		assertTrue(targets.contains(board.getCell(9, 16)));
		assertTrue(targets.contains(board.getCell(7, 18)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(10, 18), 4, true);
		targets= board.getTargets();
		assertEquals(17, targets.size());
		assertTrue(targets.contains(board.getCell(6, 18)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(9, 11), 1, true);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(9, 12)));
		assertTrue(targets.contains(board.getCell(9, 10)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(9, 11), 3, true);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(4, 12)));
		assertTrue(targets.contains(board.getCell(9, 8)));
		assertTrue(targets.contains(board.getCell(9, 14)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(9, 11), 4, true);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(4, 12)));
		assertTrue(targets.contains(board.getCell(9, 7)));
		assertTrue(targets.contains(board.getCell(10, 8)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 7), 1, true);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 8)));
		assertTrue(targets.contains(board.getCell(12, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3, true);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(10, 7)));
		assertTrue(targets.contains(board.getCell(16, 7)));
		assertTrue(targets.contains(board.getCell(15, 8)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4, true);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(12, 8)));
		assertTrue(targets.contains(board.getCell(9, 7)));
		assertTrue(targets.contains(board.getCell(17, 7)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(9, 17).setOccupied(true);
		board.calcTargets(board.getCell(7, 17), 2, true);
		board.getCell(9, 17).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(8, 16)));
		assertTrue(targets.contains(board.getCell(8, 18)));
		assertTrue(targets.contains(board.getCell(7, 19)));	
		assertFalse(targets.contains( board.getCell(9, 17)));
		
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(14, 20).setOccupied(true);
		board.getCell(10, 19).setOccupied(true);
		board.calcTargets(board.getCell(10, 18), 1, true);
		board.getCell(14, 20).setOccupied(false);
		board.getCell(10, 19).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(9, 18)));	
		assertTrue(targets.contains(board.getCell(10, 17)));	
		assertTrue(targets.contains(board.getCell(14, 20)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(13, 16).setOccupied(true);
		board.calcTargets(board.getCell(12, 13), 1, true);
		board.getCell(13, 16).setOccupied(false);
		targets= board.getTargets();
		assertEquals(0, targets.size());
		assertTrue(!targets.contains(board.getCell(13, 8)));	
		assertTrue(!targets.contains(board.getCell(9, 17)));
	}
}
