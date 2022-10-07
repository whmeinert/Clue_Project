package experiment;

import java.util.*;

public class TestBoard {
	private TestBoardCell testBoard[][];
	private Set target;
	
	public TestBoard(){
		
	}
	
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set getTargets(){
		target = new HashSet();
		target.add(-1);
		return target;
	}
	
	public TestBoardCell getCell( int row, int col ) {
		testBoard = new TestBoardCell[1][1];
		testBoard[0][0] = new TestBoardCell(-1,-1);
		return this.testBoard[0][0];
	}
}
