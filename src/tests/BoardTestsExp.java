package tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}

	@Test
	public void testAdjList() {
	
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
		
		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
		
		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
		
		cell = board.getCell(3, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
		
		/*
		TestBoardCell testCell = new TestBoardCell(2,2);
		TestBoardCell testAdj0 = new TestBoardCell(0,0);
		TestBoardCell testAdj1 = new TestBoardCell(3,3);
		TestBoardCell testAdj2 = new TestBoardCell(1,3);
		TestBoardCell testAdj3 = new TestBoardCell(3,0);
		
		testCell.addAdjacency(testAdj0);
		testCell.addAdjacency(testAdj1);
		testCell.addAdjacency(testAdj2);
		testCell.addAdjacency(testAdj3);
		
		Set<TestBoardCell> testSet = new HashSet<>();
		testSet.add(testAdj0);
		testSet.add(testAdj1);
		testSet.add(testAdj2);
		testSet.add(testAdj3);
		
		assertEquals(testSet, testCell.getAdjList());
		*/
	}
	
	
	
}
