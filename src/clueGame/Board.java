package clueGame;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import experiment.TestBoardCell;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private int COLS = 25;
	private int ROWS = 24;
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
		Object object = this.getClass().getResourceAsStream("ClueSetup306.txt");
		if (object == null) {
		    throw new FileNotFoundException("Could not find file ClueSetup306.txt");
		}
		Scanner scanner = new Scanner((InputStream)object);
		while (scanner.hasNextLine()) {
		    object = scanner.nextLine();
		    if (((String)object).substring(0, 2).contentEquals("//")) continue;
		    String[] stringArray = ((String)object).split(",");
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
		    throw new BadConfigFormatException("Room file format incorrect at line: " + (String)object);
		}
		scanner.close();
	}
	
	public void loadLayoutConfig() throws Exception{
		ArrayList<String> arrayList = new ArrayList<String>();
        InputStream inputStream = this.getClass().getResourceAsStream("ClueLayout306.csv");
        if (inputStream == null) {
            throw new FileNotFoundException("Could not find file ClueLayout306.csv");
        }
        Scanner scanner = new Scanner(inputStream);
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
        int n = 0;
        while (n < this.ROWS) {
            int n2 = 0;
            while (n2 < this.COLS) {
                this.LEFT(n, n2);
                ++n2;
            }
            ++n;
        }
    }

    private void LEFT(int n, int n2) {
        char c;
        BoardCell c2 = this.grid[n][n2];
        if (c2.isWalkway()) {
            this.PERSON(c2, n - 1, n2, c2.getDoorDirection() == DoorDirection.UP);
            this.PERSON(c2, n + 1, n2, c2.getDoorDirection() == DoorDirection.DOWN);
            this.PERSON(c2, n, n2 - 1, c2.getDoorDirection() == DoorDirection.LEFT);
            this.PERSON(c2, n, n2 + 1, c2.getDoorDirection() == DoorDirection.RIGHT);
        }
        if ((c = c2.getSecretPassage()) != '\u0000') {
            c2.getRoom().getCenterCell().addAdj(((Room)this.roomMap.get(Character.valueOf(c))).getCenterCell());
        }
    }

    private void PERSON(BoardCell c, int n, int n2, boolean bl) {
        if (n < 0 || n2 < 0 || n >= this.ROWS || n2 >= this.COLS) {
            return;
        }
        BoardCell c2 = this.grid[n][n2];
        if (c2.isUnused()) {
            return;
        }
        if (c2.isRoom()) {
            if (!bl) {
                return;
            }
            Room n3 = c2.getRoom();
            c2 = n3.getCenterCell();
            if (c.isDoorway()) {
                c.setRoom(n3);
            }
        }
        c.addAdj(c2);
        c2.addAdj(c);
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
	
	public final Room getRoom(char label) {
        return (Room)this.roomMap.get(Character.valueOf(label));
    }
	
	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
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
