package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.*;

import experiment.TestBoardCell;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private int COLS;
	private int ROWS;
	private String layoutConfigFile;
	private String setupConfigFile;
	Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private String configCSV;
	private String configTXT;
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
    	this.loadDataFiles();
        this.fillTable();
    }
	
    // Calls functions to load setup file and layout file and catches any errors thrown
    public final void loadDataFiles() {
        try {
            this.loadSetupConfig();
            this.loadLayoutConfig();
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    // Load Clue setup from file and read data into roomMap
	public void loadSetupConfig() throws Exception {
		FileReader reader;
		try {
			reader = new FileReader(configTXT);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Could not find file " + configTXT);
		};
		Scanner scanner = new Scanner(reader);
		while (scanner.hasNextLine()) {
			String currLine = scanner.nextLine();
		    if ((currLine).substring(0, 2).contentEquals("//")) continue;
		    String[] stringArray = (currLine).split(",");
		    char c = '\u0000';
		    while (c < stringArray.length) {
		        stringArray[c] = stringArray[c].trim();
		        ++c;
		    }
		    if (stringArray[0].contentEquals("Room")) {
		        char c2 = stringArray[2].charAt(0);
		        Room room = new Room(stringArray[1]);
		        this.roomMap.put(Character.valueOf(c2), room);
		        continue;
		    }
		    if (stringArray[0].contentEquals("Space")) {
		        c = stringArray[2].charAt(0);
		        Room room = new Room(stringArray[1]);
		        this.roomMap.put(Character.valueOf(c), room);
		        continue;
		    }
		    scanner.close();
		    throw new BadConfigFormatException("Room file format incorrect at line: " + currLine);
		}
		scanner.close();
	}
	
	// Load Clue layout from file and read data into grid
	public void loadLayoutConfig() throws Exception{
		ArrayList<String> boardRows = new ArrayList<String>();
		FileReader reader;
		
		try {
			reader = new FileReader(configCSV);
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Could not find file " + configCSV);
		};
		
        Scanner scanner = new Scanner(reader);
        
        int n = 0;
        
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            boardRows.add(string);
            if (n == 0) {
                String[] stringArray = string.split(",");
                this.COLS = stringArray.length;
            }
            ++n;
        }
        
        scanner.close();
        
        this.ROWS = n;
        this.grid = new BoardCell[this.ROWS][this.COLS];
        
        n = 0;
        
        for (String string : boardRows) {
            String[] stringArray = string.split(",");
            
            if (this.COLS != stringArray.length) {
                scanner.close();
                throw new BadConfigFormatException("Rows do not all have the same number of columns");
            }
            
            int k = 0;
            
            while (k < this.COLS) {
                Room currRoom;
                char location = stringArray[k].charAt(0);
                char dir = '\u0000';
                if (stringArray[k].length() > 1) {
                	dir = stringArray[k].charAt(1);
                }
                if ((currRoom = (Room)this.roomMap.get(Character.valueOf(location))) == null) {
                    scanner.close();
                    throw new BadConfigFormatException("Room not defined " + stringArray[k]);
                }
                this.grid[n][k] = new BoardCell(n, k, currRoom, location, dir);
                if (dir == '*') {
                	currRoom.setCenterCell(this.grid[n][k]);
                }
                if (dir == '#') {
                	currRoom.setLabelCell(this.grid[n][k]);
                }
                ++k;
            }
            ++n;
        }
	}
	
	// Loops over every row and column and call function every for every cell
	private void fillTable() {
        int currRow = 0;
        while (currRow < this.ROWS) {
            int currCol = 0;
            while (currCol < this.COLS) {
            	fillCells(currRow, currCol);
                ++currCol;
            }
            ++currRow;
        }
    }
	
	// For every cell fill in its adjList
	private void fillCells(int currRow, int currCol) {
		BoardCell currCell = this.grid[currRow][currCol];
		
		if (currCell.isWalkway()) {
		    this.fillAdj(currCell, currRow-1, currCol, currCell.getDoorDirection() == DoorDirection.UP);
		    this.fillAdj(currCell, currRow+1, currCol, currCell.getDoorDirection() == DoorDirection.DOWN);
		    this.fillAdj(currCell, currRow, currCol-1, currCell.getDoorDirection() == DoorDirection.LEFT);
		    this.fillAdj(currCell, currRow, currCol+1, currCell.getDoorDirection() == DoorDirection.RIGHT);
		}
		
		if ((currCell.getSecretPassage()) != '\u0000') {
		    currCell.getRoom().getCenterCell().addAdj(((Room)this.roomMap.get(Character.valueOf(currCell.getSecretPassage()))).getCenterCell());
		}
	}

	// Fill in adjList and test if the cell is unused or a room or a door
    private void fillAdj(BoardCell cell, int currRow, int currCol, boolean isDoor) {
        if (currRow < 0 || currCol < 0 || currRow >= this.ROWS || currCol >= this.COLS) {
            return;
        }
        BoardCell currCell = this.grid[currRow][currCol];
        if (currCell.isUnused()) {
            return;
        }
        if (currCell.isRoom()) {
            if (!isDoor) {
                return;
            }
            Room room = currCell.getRoom();
            currCell = room.getCenterCell();
            if (cell.isDoorway()) {
                cell.setRoom(room);
            }
        }
        cell.addAdj(currCell);
        currCell.addAdj(cell);
    }
	
    // gets the list of targets
	public final Set<BoardCell> getTargets() {
        return this.targets;
    }
	
	// calculates the targets for the given cell
	public final void calcTargets(BoardCell cell, int numMoves) {
        this.targets = new HashSet();
        this.visited = new HashSet();
        this.visited.add(cell);
        this.calcRecurse(cell, numMoves);
    }

	// the recursive call to calculate all possible targets
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
    
    
    // Getters and setters for private variables
    
    public final BoardCell getCell(int row, int col) {
        return this.grid[row][col];
    }
    
	public void setConfigFiles(String string, String string2) {
		this.configCSV = string;
		this.configTXT = string2;
		
	}
	
	public final Room getRoom(char label) {
        return (Room)this.roomMap.get(Character.valueOf(label));
    }
	
	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}
	
	public int getNumRows() {
		return this.ROWS;
	}
	
	public int getNumColumns() {
		return this.COLS;
	}
	public Set<BoardCell> getAdjList(int i, int j) {
		return this.grid[i][j].getAdjList();
	}

}
