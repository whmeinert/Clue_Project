package clueGame;

import java.util.*;

public class BoardCell {
	public int row, col;
	private final Boolean isRoom = false;
	private Boolean isOccupied = false;
	private final Set<BoardCell> adjList = new HashSet<>();
	private char initial;
	private final char location;
	private DoorDirection doorDirection = DoorDirection.NONE;
	private Boolean roomLabel = false;
	private Boolean roomCenter = false;
	private char secretPassage;
	Room room;

	
	// initializes cells variables and finds what the direction, label, center, or secret passage is
	public BoardCell(int row, int col, Room room, char location, char dir) {
		this.row = row;
		this.col = col;
		this.location = location;
		this.room = room;
		
		// set the direction of the cell or if it is a label or the center or a secret passage
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
	
	
	// Getters and setters for private variables
	
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
		// set the location to be occupied if the location is a walkway
		if (this.location == 'W') {
			this.isOccupied = isOcc;
		}
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

	public final int getColumn() {
		return this.col;
	}

	public final int getRow() {
		return this.row;
	}
}
