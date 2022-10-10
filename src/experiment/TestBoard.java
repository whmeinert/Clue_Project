package experiment;

import java.util.*;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	
	public TestBoard(){
		this.grid = new TestBoardCell[ROWS][COLS];
		
		// Generate game board and have all spaces be walk ways
		for( int i = 0; i < ROWS ; i++) {
			for (int j = 0; j < COLS ; j++) {
				this.grid[i][j] = new TestBoardCell(i,j,'W');
				this.grid[i][j].setOccupied(false);
				this.grid[i][j].setRoom(false);
			}
		}
		
		// Try and add each side as an adjacency
		for( int i = 0; i < ROWS ; i++) {
			for (int j = 0; j < COLS ; j++) {
				try {
					this.grid[i][j].addAdj(this.grid[i-1][j]);
				} catch (Exception e) {
				}try {
					this.grid[i][j].addAdj(this.grid[i][j-1]);
				} catch (Exception e) {
				}try {
					this.grid[i][j].addAdj(this.grid[i+1][j]);
				} catch (Exception e) {
				}try {
					this.grid[i][j].addAdj(this.grid[i][j+1]);
				} catch (Exception e) {
				}
			}
		}
	}
	
	public final Set<TestBoardCell> getTargets() {
        return this.targets;
    }
	
	public final void calcTargets(TestBoardCell cell, int numMoves) {
        this.targets = new HashSet();
        this.visited = new HashSet();
        this.visited.add(cell);
        this.calcRecurse(cell, numMoves);
    }

    private void calcRecurse(TestBoardCell cell, int numMoves) {
        Set<TestBoardCell> adj = cell.getAdjList();
        for (TestBoardCell move : adj) {
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
    
    public final TestBoardCell getCell(int row, int col) {
        return this.grid[row][col];
    }

}
