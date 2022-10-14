package clueGame;

import java.util.*;

public class BoardCell {
	public int row, col;
	private Boolean isRoom, isOccupied;
	Set<BoardCell> adjList = new HashSet();
	private char initial;
	private char location;
	private DoorDirection doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
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

	public boolean isWalkway() {
		// TODO Auto-generated method stub
		return false;
	}

	public Room getRoom() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isUnused() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
