package experiment;

import java.util.*;

public class TestBoardCell {
	private Set adjList = new HashSet();

	public TestBoardCell(int row, int col) {
		
	}
	
	public void addAdjacency(TestBoardCell cell) {
		
	}
	
	public Set<TestBoardCell> getAdjList(){
		adjList.add(0);
		return this.adjList;
	}
	
	public void setRoom(boolean inRoom) {
		
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public void setOccupied(boolean isOcc) {
		
	}
	
	public boolean getOccupied() {
		return false;
	}
	
}
