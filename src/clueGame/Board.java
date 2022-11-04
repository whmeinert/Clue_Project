package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private int cols;
	private int rows;
	private String layoutConfigFile;
	private String setupConfigFile;
    private ArrayList hand;
    private Solution sol;
    private ComputerPlayer add;
    private ArrayList WEAPON;

    private Player addMouseListener;
    private ArrayList addToHand;
	Map<Character, Room> roomMap = new HashMap<Character, Room>();
    Random startLoc = new Random();
	
	/*
     * variable and methods used for singleton pattern
     */
    private static final Board theInstance = new Board();

    // constructor is private to ensure only one can be created
    private Board() {
           super();
    }
    
    // this method returns the only Board
    public static Board getInstance() {
           return theInstance;
    }
    
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() {
        this.WEAPON = new ArrayList();
        this.addToHand = new ArrayList();
    	this.loadDataFiles();
        this.fillTable();
        this.deal();
    }
	
    // Calls functions to load setup file and layout file and catches any errors thrown
    public final void loadDataFiles() {
        try {
            this.loadSetupConfig();
            this.loadLayoutConfig();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    // Load Clue setup from file and read data into roomMap
	public void loadSetupConfig() throws Exception {
		FileReader reader;
		
		try {
			reader = new FileReader(setupConfigFile);
		} catch (FileNotFoundException e) {		// Catch file not found exception
			throw new FileNotFoundException("Could not find file " + setupConfigFile);
		};
		
		Scanner scanner = new Scanner(reader);
		
		// loop through config file
		while (scanner.hasNextLine()) {
			String currLine = scanner.nextLine();
		    if ((currLine).substring(0, 2).contentEquals("//")) continue;
		    String[] stringArray = (currLine).split(",");
		    int n = 0;
		    
		    while (n < stringArray.length) {
		        stringArray[n] = stringArray[n].trim();
		        ++n;
		    }
		    
		    char descriptor = '\u0000';
		    // check if current line is a room
		    if (stringArray[0].contentEquals("Room")) {
                Card card = new Card(stringArray[1], CardType.ROOM);
		        char currLoc = stringArray[2].charAt(0);
		        Room room = new Room(stringArray[1], card);
		        this.roomMap.put(currLoc, room);
		        continue;
		    }
		    
		    // check if current line is a space
		    if (stringArray[0].contentEquals("Space")) {
		        descriptor = stringArray[2].charAt(0);
		        Room room = new Room(stringArray[1], null);
		        this.roomMap.put(descriptor, room);
		        continue;
		    }

            if (stringArray[0].contentEquals("Player")) {
                Player m;
                if (stringArray[2].contentEquals("human")) {
                    this.add = new ComputerPlayer(stringArray[1], Integer.parseInt(stringArray[3]), Integer.parseInt(stringArray[4]), stringArray[5]);
                    m = this.add;
                } else {
                    m = new HumanPlayer(stringArray[1], Integer.parseInt(stringArray[3]), Integer.parseInt(stringArray[4]), stringArray[5]);
                }
                this.WEAPON.add(m);
                this.addToHand.add(new Card(stringArray[1], CardType.PERSON));
                continue;
            }
            if (stringArray[0].contentEquals("Weapon")) {
                this.addToHand.add(new Card(stringArray[1], CardType.WEAPON));
                continue;
            }
		    
		    scanner.close();
		    // if line is not a room or space throw BadConfigFormatExeption
		    throw new BadConfigFormatException("Room file format incorrect at line: " + currLine);
		}
		scanner.close();
	}
	
	// Load Clue layout from file and read data into grid
	public void loadLayoutConfig() throws Exception{
		ArrayList<String> boardRows = new ArrayList<String>();
		FileReader reader;
		
		try {
			reader = new FileReader(layoutConfigFile);
		} 
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Could not find file " + layoutConfigFile);
		};
		
        Scanner scanner = new Scanner(reader);
        
        int numRows = 0;
        // loop through file and count number of rows and columns
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            boardRows.add(string);
            
            if (numRows == 0) {
                String[] stringArray = string.split(",");
                this.cols = stringArray.length;
            }
            ++numRows;
        }
        
        scanner.close();
        
        this.rows = numRows;
        this.grid = new BoardCell[this.rows][this.cols];
        
        numRows = 0;
        // loop over every row and set each column
        for (String string : boardRows) {
            String[] stringArray = string.split(",");
            
            if (this.cols != stringArray.length) {
                scanner.close();
                throw new BadConfigFormatException("Rows do not all have the same number of columns");
            }
            
            int numCols = 0;
            // loop over every column and set the room and its center and label and its secret passages
            while (numCols < this.cols) {
                Room currRoom;
                char location = stringArray[numCols].charAt(0);
                char descriptor = '\u0000';
                
                if (stringArray[numCols].length() > 1) {
                	descriptor = stringArray[numCols].charAt(1);
                }
                
                if ((currRoom = this.roomMap.get(location)) == null) {
                    scanner.close();
                    throw new BadConfigFormatException("Room not defined " + stringArray[numCols]);
                }
                
                this.grid[numRows][numCols] = new BoardCell(numRows, numCols, currRoom, location, descriptor);
                
                if (descriptor == '*') {
                	currRoom.setCenterCell(this.grid[numRows][numCols]);
                }
                
                if (descriptor == '#') {
                	currRoom.setLabelCell(this.grid[numRows][numCols]);
                }
                ++numCols;
            }
            ++numRows;
        }
	}
	
	// Loops over every row and column and call function every for every cell
	private void fillTable() {
        int currRow = 0;
        while (currRow < this.rows) {
            int currCol = 0;
            
            while (currCol < this.cols) {
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
		    currCell.getRoom().getCenterCell().addAdj(((Room)this.roomMap.get(currCell.getSecretPassage())).getCenterCell());
		}
	}

	// Fill in adjList and test if the cell is unused or a room or a door
    private void fillAdj(BoardCell cell, int currRow, int currCol, boolean isDoor) {
        if (currRow < 0 || currCol < 0 || currRow >= this.rows || currCol >= this.cols) {
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
        this.targets = new HashSet<>();
        this.visited = new HashSet<>();
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
            }
            else if (numMoves == 1) {
                this.targets.add(move);
            }
            else {
                this.calcRecurse(move, numMoves - 1);
            }
            
            this.visited.remove(move);
        }
    }

    public final void deal() {
        int n = 0;
        Collections.shuffle(this.addToHand, this.startLoc);
        this.sol = new Solution();
        for (Object object : this.WEAPON) {
            ((Player)object).clearCards();
        }
        for (Object object : this.addToHand) {
            if (((Card)object).getCardType() == CardType.PERSON && this.sol.person == null) {
                this.sol.person = (Card)object;
                continue;
            }
            if (((Card)object).getCardType() == CardType.ROOM && this.sol.room == null) {
                this.sol.room = (Card)object;
                continue;
            }
            if (((Card)object).getCardType() == CardType.WEAPON && this.sol.weapon == null) {
                this.sol.weapon = (Card)object;
                continue;
            }
            Player m = (Player)this.WEAPON.get(n);
            m.addToHand((Card)object);
            ((Card)object).setHoldingPlayer(m);
            n = (n + 1) % this.WEAPON.size();
        }
        Collections.sort(this.addToHand);
    }
    
    
    // Getters and setters for private variables
    
    public final BoardCell getCell(int row, int col) {
        return this.grid[row][col];
    }
    
	public void setConfigFiles(String csvFile, String txtFile) {
		this.layoutConfigFile = csvFile;
		this.setupConfigFile = txtFile;
		
	}
	
	public final Room getRoom(char label) {
        return this.roomMap.get(label);
    }
	
	public Room getRoom(BoardCell cell) {
		return cell.getRoom();
	}
	
	public int getNumRows() {
		return this.rows;
	}
	
	public int getNumColumns() {
		return this.cols;
	}
	
	public Set<BoardCell> getAdjList(int i, int j) {
		return this.grid[i][j].getAdjList();
	}

    public final ArrayList getCards() {
        return this.hand;
    }

}
