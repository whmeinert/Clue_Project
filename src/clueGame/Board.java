package clueGame;

import java.util.*;

import experiment.TestBoardCell;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	final static int COLS = 25;
	final static int ROWS = 24;
	private String layoutConfigFile;
	private String setupConfigFile;
	Map<Character, Room> roomMap;
	  /*
     * variable and methods used for singleton pattern
     */
     private static Board theInstance = new Board();
     // constructor is private to ensure only one can be created
     private Board() {
            super() ;
     }
     // this method returns the only Board
     public static Board getInstance() {
            return theInstance;
     }
     /*
      * initialize the board (since we are using singleton pattern)
      */
     public void initialize()
     {
    	 this.grid = new BoardCell[ROWS][COLS];
    	 for( int i = 0; i < ROWS ; i++) {
 			 for (int j = 0; j < COLS ; j++) {
 				this.grid[i][j] = new BoardCell(i,j);
 				this.grid[i][j].setOccupied(false);
 				this.grid[i][j].setRoom(false);
 			 }
 		 }
     }
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	public final Set<BoardCell> getTargets() {
        return this.targets;
    }
	
	public final void calcTargets(BoardCell cell, int numMoves) {
        this.targets = new HashSet();
        this.visited = new HashSet();
        this.visited.add(cell);
        this.calcRecurse(cell, numMoves);
    }

    private void calcRecurse(BoardCell cell, int numMoves) {
        Set<BoardCell> adj = cell.getAdjList();
        for (BoardCell move : adj) {
            if (this.visited.contains(move) || move.isOccupied()) {
            	continue;
            }
            
            this.visited.add(move);
            if (move.isRoom()) {
                this.targets.add(move);
            } else if (numMoves == 1) {
                this.targets.add(move);
            } else {
                this.calcRecurse(move, numMoves - 1);
            }
            
            this.visited.remove(move);
        }
    }
    
    public final BoardCell getCell(int row, int col) {
        return this.grid[row][col];
    }
	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
	
	public Room getRoom(char cell) {
		Room room = new Room();
		return room;
	}
	
	public Room getRoom(BoardCell cell) {
		Room room = new Room();
		return room;
	}
	
	public String getName() {
		return "";
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return -1;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return -1;
	}

}
