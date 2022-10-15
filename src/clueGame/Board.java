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
        this.DOWN();
    }
	
    public final void loadDataFiles() {
        try {
            this.loadSetupConfig();
            this.loadLayoutConfig();
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
     
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
	
	public void loadLayoutConfig() throws Exception{
		ArrayList<String> arrayList = new ArrayList<String>();
		FileReader reader;
		try {
			reader = new FileReader(configCSV);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Could not find file " + configCSV);
		};
        Scanner scanner = new Scanner(reader);
        int n = 0;
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            arrayList.add(string);
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
        for (String string : arrayList) {
            String[] stringArray = string.split(",");
            if (this.COLS != stringArray.length) {
                scanner.close();
                throw new BadConfigFormatException("Rows do not all have the same number of columns");
            }
            int n2 = 0;
            while (n2 < this.COLS) {
                Room n3;
                char c = stringArray[n2].charAt(0);
                char c2 = '\u0000';
                if (stringArray[n2].length() > 1) {
                    c2 = stringArray[n2].charAt(1);
                }
                if ((n3 = (Room)this.roomMap.get(Character.valueOf(c))) == null) {
                    scanner.close();
                    throw new BadConfigFormatException("Room not defined " + stringArray[n2]);
                }
                this.grid[n][n2] = new BoardCell(n, n2, n3, c, c2);
                if (c2 == '*') {
                    n3.setCenterCell(this.grid[n][n2]);
                }
                if (c2 == '#') {
                    n3.setLabelCell(this.grid[n][n2]);
                }
                ++n2;
            }
            ++n;
        }
	}
	
	private void DOWN() {
        int currRow = 0;
        while (currRow < this.ROWS) {
            int currCol = 0;
            while (currCol < this.COLS) {
                this.LEFT(currRow, currCol);
                ++currCol;
            }
            ++currRow;
        }
    }

    private void LEFT(int n, int n2) {
        BoardCell currCell = this.grid[n][n2];
        if (currCell.isWalkway()) {
            this.PERSON(currCell, n - 1, n2, currCell.getDoorDirection() == DoorDirection.UP);
            this.PERSON(currCell, n + 1, n2, currCell.getDoorDirection() == DoorDirection.DOWN);
            this.PERSON(currCell, n, n2 - 1, currCell.getDoorDirection() == DoorDirection.LEFT);
            this.PERSON(currCell, n, n2 + 1, currCell.getDoorDirection() == DoorDirection.RIGHT);
        }
        if ((currCell.getSecretPassage()) != '\u0000') {
            currCell.getRoom().getCenterCell().addAdj(((Room)this.roomMap.get(Character.valueOf(currCell.getSecretPassage()))).getCenterCell());
        }
    }

    private void PERSON(BoardCell cell, int currRow, int currCol, boolean bl) {
        if (currRow < 0 || currCol < 0 || currRow >= this.ROWS || currCol >= this.COLS) {
            return;
        }
        BoardCell currCell = this.grid[currRow][currCol];
        if (currCell.isUnused()) {
            return;
        }
        if (currCell.isRoom()) {
            if (!bl) {
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

}
