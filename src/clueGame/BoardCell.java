package clueGame;

import java.util.*;

public class BoardCell {
	public int row, col;
	private Boolean isRoom = false, isOccupied = false;
	Set<BoardCell> adjList = new HashSet();
	private char initial;
	private char location;
	private DoorDirection doorDirection = DoorDirection.NONE;
	private Boolean roomLabel = false;
	private Boolean roomCenter = false;
	private char secretPassage;
	Room room;
	

	public BoardCell(int row, int col, Room room, char location, char dir) {
		this.row = row;
		this.col = col;
		this.location = location;
		this.room = room;
		switch (dir) {
        	case '<': {
	        	this.doorDirection = DoorDirection.LEFT;
	            break;
	        }
	        case '^': {
	            this.doorDirection = DoorDirection.UP;
	            break;
	        }
	        case '>': {
	            this.doorDirection = DoorDirection.RIGHT;
	            break;
	        }
	        case 'v': {
	            this.doorDirection = DoorDirection.DOWN;
	            break;
	        }
	        case '#': {
	            this.roomLabel = true;
	            break;
	        }
	        case '*': {
	            this.roomCenter = true;
	            break;
	        }
	        default: {
	            this.secretPassage = dir;
	        }
	    }
	}
	
	public final Set<BoardCell> getAdjList() {
        return this.adjList;
    }

    public final void addAdj(BoardCell cell) {
        this.adjList.add(cell);
    }
	
	public void setRoom(Room room) {
        this.room = room;
	}
	
	public final boolean isRoom() {
        return this.location != 'W' && this.location != 'X';
    }
	
	public void setOccupied(boolean isOcc) {
		this.isOccupied = isOcc;
	}
	
	public boolean isOccupied() {
		return this.isOccupied;
	}

	public boolean isDoorway() {
		return this.doorDirection != DoorDirection.NONE;
	}

	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public char getSecretPassage() {
		return this.secretPassage;
	}

	public boolean isWalkway() {
		return this.location == 'W';
	}

	public Room getRoom() {
		return this.room;
	}

	public boolean isUnused() {
		return this.location == 'X';
	}
	
	
	
}
