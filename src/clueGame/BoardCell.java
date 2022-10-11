package clueGame;

import java.util.*;

public class BoardCell {
	public int row, col;
	private Boolean isRoom, isOccupied;
	Set<BoardCell> adjList = new HashSet();
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private char secretPassage;
	

	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public final Set<BoardCell> getAdjList() {
        return this.adjList;
    }

    public final void addAdj(BoardCell cell) {
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

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
