package experiment;

import java.util.*;

public class TestBoardCell {
	public int row, col;
	private Boolean isRoom, isOccupied;
	Set<TestBoardCell> adjList = new HashSet();
	private char location;

	public TestBoardCell(int row, int col, char loc) {
		this.row = row;
		this.col = col;
		this.location = loc;
	}
	
	public final Set<TestBoardCell> getAdjList() {
        return this.adjList;
    }

    public final void addAdj(TestBoardCell cell) {
        this.adjList.add(cell);
    }
	
	public void setRoom(boolean inRoom) {
        this.isRoom = inRoom;
	}
	
	public final boolean isRoom() {
        return isRoom;
    }
	
	public void setOccupied(boolean isOcc) {
		this.isOccupied = isOcc;
	}
	
	public boolean isOccupied() {
		return this.isOccupied;
	}
	
}
