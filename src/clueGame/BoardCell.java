package clueGame;

import java.awt.*;
import java.util.*;

public class BoardCell {
	private boolean isTarget = false;
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
	private int score;

	
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

	// Draw a cell of the board with the proper color and location
	public final void drawCell(Graphics2D graphics2D, int n, int n2, int n3) {
		int n4 = this.row * n + n3;
		int n5 = this.col * n + n2;
		if (this.isTarget){
			graphics2D.setColor(Color.CYAN);
		} else if (this.isWalkway()) {
			graphics2D.setColor(Color.YELLOW);
		} else if (this.isUnused()) {
			graphics2D.setColor(Color.BLACK);
		} else {
			graphics2D.setColor(Color.LIGHT_GRAY);
		}
		graphics2D.fillRect(n5, n4, n, n);
		if (this.isWalkway()) {
			graphics2D.setColor(Color.black);
			graphics2D.drawRect(n5, n4, n - 1, n - 1);
		}
		// Draw the secret passage slightly differently and draw a "S" to indicate that it is a secret passage
		if (this.secretPassage != '\u0000') {
			graphics2D.setColor(Color.black);
			graphics2D.fillRect(n5, n4, n, n);
			int[] nArray = new int[5];
			int[] nArray2 = new int[5];
			nArray[0] = n5 + 1;
			nArray2[0] = n4 + 1;
			nArray[1] = n5 + n * 4 / 5;
			nArray2[1] = n4 + n / 5;
			nArray[2] = nArray[1];
			nArray2[2] = n4 + n;
			nArray[3] = n5 + 1;
			nArray2[3] = nArray2[2];
			nArray[4] = nArray[0];
			nArray2[4] = nArray2[0];
			graphics2D.setColor(Color.yellow);
			graphics2D.fillPolygon(nArray, nArray2, 4);
			int n6 = n * 2 / 3;
			int n7 = n5 + n / 5;
			int n8 = n4 + n * 4 / 5;
			Font font = new Font("Comic Sans MS", 1, n6);
			graphics2D.setFont(font);
			graphics2D.setColor(Color.BLUE);
			graphics2D.drawString("S", n7, n8);
		}
	}

	// Draw a door at the proper location
	public final void drawDoor(Graphics2D graphics2D, int n, int n2, int n3) {
		int n4;
		int n5;
		int n6 = this.row * n + n3;
		int n7 = this.col * n + n2;
		int n8 = 6;
		if (this.doorDirection == DoorDirection.LEFT) {
			n5 = n / n8;
			n4 = n;
			n7 -= n5;
		} else if (this.doorDirection == DoorDirection.RIGHT) {
			n5 = n / n8;
			n4 = n;
			n7 += n;
		} else if (this.doorDirection == DoorDirection.UP) {
			n5 = n;
			n4 = n / n8;
			n6 -= n4;
		} else if (this.doorDirection == DoorDirection.DOWN) {
			n5 = n;
			n4 = n / n8;
			n6 += n;
		} else {
			return;
		}
		graphics2D.setColor(Color.blue);
		graphics2D.fillRect(n7, n6, n5, n4);
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

	public final Room getToRoom() {
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

	public final void setScore(int n) {
		this.score = n;
	}

	public final int getScore() {
		return this.score;
	}

	public final void setHighlight(boolean bl) {
		this.isTarget = bl;
	}
}
