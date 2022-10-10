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
		//System.out.println(this.row + " " + this.col);
        return this.adjList;
    }

    public final void addAdj(TestBoardCell cell) {
    	//System.out.println(cell.row + " " + cell.col);
        this.adjList.add(cell);
    }
	
	public void setRoom(boolean inRoom) {
		if (this.location != 'W' && this.location != 'X') {
            this.isRoom = inRoom;
        }
	}
	
	public final boolean isRoom() {
        return (this.location != 'W' && this.location != 'X');
    }
	
	public void setOccupied(boolean isOcc) {
		if (this.location == 'W') {
            this.isOccupied = isOcc;
        }
	}
	
	public boolean isOccupied() {
		return this.isOccupied;
	}
	
}
