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
		for( int i = 0; i < ROWS ; i++) {
			for (int j = 0; j < COLS ; j++) {
				this.grid[i][j] = new TestBoardCell(i,j,'W');
				this.grid[i][j].setOccupied(false);
				this.grid[i][j].setRoom(false);
			}
		}
		
		for( int i = 0; i < ROWS ; i++) {
			for (int j = 0; j < COLS ; j++) {
				if(i != 0 && j != 0 && i != ROWS-1 && j != COLS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i-1][j]);
					this.grid[i][j].addAdj(this.grid[i][j-1]);
					this.grid[i][j].addAdj(this.grid[i+1][j]);
					this.grid[i][j].addAdj(this.grid[i][j+1]);
				} else if (i == 0 && j != 0 && j < COLS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i][j-1]);
					this.grid[i][j].addAdj(this.grid[i+1][j]);
					this.grid[i][j].addAdj(this.grid[i][j+1]);
				} else if(j == 0 && i != 0 && i < ROWS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i-1][j]);
					this.grid[i][j].addAdj(this.grid[i+1][j]);
					this.grid[i][j].addAdj(this.grid[i][j+1]);
				} else if(i == 0 && j == 0) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i+1][j]);
					this.grid[i][j].addAdj(this.grid[i][j+1]);
				} else if (i == 0 && j == COLS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i+1][j]);
					this.grid[i][j].addAdj(this.grid[i][j-1]);
				} else if(j == 0 && i == ROWS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i][j+1]);
					this.grid[i][j].addAdj(this.grid[i-1][j]);
				} else if (i == ROWS && j < 0 && j < COLS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i][j-1]);
					this.grid[i][j].addAdj(this.grid[i-1][j]);
					this.grid[i][j].addAdj(this.grid[i][j+1]);
				} else if(j == COLS-1 && i != 0 && i < ROWS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i-1][j]);
					this.grid[i][j].addAdj(this.grid[i+1][j]);
					this.grid[i][j].addAdj(this.grid[i][j-1]);
				} else if(i == ROWS-1 && j == COLS-1) {
					//System.out.println(i + " " + j + ": ");
					this.grid[i][j].addAdj(this.grid[i-1][j]);
					this.grid[i][j].addAdj(this.grid[i][j-1]);
				}
				//System.out.println();
			}
		}
	}
	
	public final Set<TestBoardCell> getTargets() {
		for(TestBoardCell c2 : targets) {
			System.out.println(c2.row + " " + c2.col );
		}
        return this.targets;
    }
	
	public final void calcTargets(TestBoardCell c, int n, boolean bl) {
        this.targets = new HashSet();
        this.visited = new HashSet();
        if (bl) {
            this.targets.add(c);
        }
        this.visited.add(c);
        this.RIGHT(c, n);
    }

    private void RIGHT(TestBoardCell c, int n) {
        Set<TestBoardCell> set = c.getAdjList();
        for (TestBoardCell c2 : set) {
            if (this.visited.contains(c2) || c2.isOccupied()) continue;
            this.visited.add(c2);
            if (c2.isRoom()) {
                this.targets.add(c2);
            } else if (n == 1) {
                this.targets.add(c2);
            } else {
                this.RIGHT(c2, n - 1);
            }
            this.visited.remove(c2);
        }
    }
    
    public final TestBoardCell getCell(int n, int n2) {
        return this.grid[n][n2];
    }

}
