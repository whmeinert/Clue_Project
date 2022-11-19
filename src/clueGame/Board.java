package clueGame;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board extends JPanel {
    private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private int cols;
	private int rows;
	private String layoutConfigFile;
	private String setupConfigFile;
    private Solution solution;
    private HumanPlayer humanPlayer;
    private ArrayList<Player> players;
    private ArrayList<Card> cards;
	Map<Character, Room> roomMap = new HashMap<>();
    Random random = new Random();
	
	/*
     * variable and methods used for singleton pattern
     */
    private static final Board theInstance = new Board();
    private int boardWidth;
    private int boardHeight;
    private int cellSize;
    private ClueGame currGame;
    private GameControlPanel checkAccusation;
    private KnownCardsPanel clearCards;
    private int addAdj = -1;
    private Player addMouseListener;

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
        this.players = new ArrayList<Player>();
        this.cards = new ArrayList<Card>();
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
		    
		    char descriptor = '\u0000';  // set to null character

		    // check if current line is a room
		    if (stringArray[0].contentEquals("Room")) {
                Card card = new Card(stringArray[1], CardType.ROOM);
		        char currLoc = stringArray[2].charAt(0);
		        Room room = new Room(stringArray[1], card);
		        this.roomMap.put(currLoc, room);
                this.cards.add(card);
		        continue;
		    }
		    
		    // check if current line is a space
		    if (stringArray[0].contentEquals("Space")) {
		        descriptor = stringArray[2].charAt(0);
		        Room room = new Room(stringArray[1], null);
		        this.roomMap.put(descriptor, room);
		        continue;
		    }

            // check if current line is a space
            if (stringArray[0].contentEquals("Player")) {
                Player currPlayer;
                // Check if current player is a human or computer
                if (stringArray[2].contentEquals("human")) {
                    this.humanPlayer = new HumanPlayer(stringArray[1], Integer.parseInt(stringArray[3]), Integer.parseInt(stringArray[4]), stringArray[5]);
                    currPlayer = this.humanPlayer;
                } else {
                    currPlayer = new ComputerPlayer(stringArray[1], Integer.parseInt(stringArray[3]), Integer.parseInt(stringArray[4]), stringArray[5]);
                }
                this.players.add(currPlayer);
                this.cards.add(new Card(stringArray[1], CardType.PERSON));
                continue;
            }

            // check if current line is a weapon
            if (stringArray[0].contentEquals("Weapon")) {
                this.cards.add(new Card(stringArray[1], CardType.WEAPON));
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
	public final void calcTargets(BoardCell cell, int numMoves, boolean isHuman) {
        this.targets = new HashSet<>();
        this.visited = new HashSet<>();
        if (isHuman){
            this.targets.add(cell);
        }
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

        // Randomly shuffle cards
        Collections.shuffle(this.cards, this.random);

        this.solution = new Solution();  // initialize solution

        // initialize card array for all players
        for (Player player : this.players) {
            player.clearCards();
        }

        // Give cards to players
        for (Card card : this.cards) {
            // Set first PERSON type card to solution if solution is not yet set
            if (card.getCardType() == CardType.PERSON && this.solution.person == null) {
                this.solution.person = card;
                continue;
            }
            // Set first ROOM type card to solution if solution is not yet set
            if (card.getCardType() == CardType.ROOM && this.solution.room == null) {
                this.solution.room = card;
                continue;
            }
            // Set first WEAPON type card to solution if solution is not yet set
            if (card.getCardType() == CardType.WEAPON && this.solution.weapon == null) {
                this.solution.weapon = card;
                continue;
            }

            // get current player and give current card
            Player player = this.players.get(n);
            player.addToHand(card);

            // set current player to be holder of current card
            card.setHoldingPlayer(player);

            // Increment so cards are randomly distributed
            n = (n + 1) % this.players.size();
        }
        Collections.sort(this.cards);
    }

    public final boolean checkAccusation(Solution solution) {
        return solution.person.equals(this.solution.person) && solution.weapon.equals(this.solution.weapon) && solution.room.equals(this.solution.room);
    }

    public final Card handleSuggestion(Solution solution, Player currPlayer) {
        Player otherPlayer;
        Card card;
        int n = this.players.indexOf(currPlayer);
        // loop over every player and check if other players have a card that disproves the current players suggestion
        do {
            if ((otherPlayer = (Player)this.players.get(n = (n + 1) % this.players.size())) != currPlayer) continue;
            return null;
        } while ((card = otherPlayer.disproveSuggestion(solution)) == null);
        return card;
    }

    @Override
    public final void paintComponent(Graphics graphics) {
        this.boardWidth = this.getWidth();
        this.boardHeight = this.getHeight();
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, this.boardWidth, this.boardHeight);
        // Find the max cell size to fit in the current panel
        int n2 = this.boardWidth / (this.cols);
        int n3 = this.boardHeight / (this.rows);
        this.cellSize = Math.min(n2, n3);
        this.cellSize = Math.max(this.cellSize, 4);
        int maxWidth = Math.max(0, (this.boardWidth - this.cellSize * this.cols) / 2);
        int maxHeight = Math.max(0, (this.boardHeight - this.cellSize * this.rows) / 2);
        // Draw all cells
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.grid[i][j].drawCell(graphics2D, this.cellSize, maxWidth, maxHeight);
            }
        }
        // Draw Doors
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.grid[i][j].drawDoor(graphics2D, this.cellSize, maxWidth, maxHeight);
            }
        }
        // Draw Labels
        for (Room room : this.roomMap.values()) {
            room.drawLabel(graphics2D, this.cellSize, maxWidth, maxHeight);
        }
        // Draw Players
        for (Player player : this.players) {
            player.drawPlayer(graphics2D, this.cellSize, maxWidth, maxHeight);
        }
    }

    public final void nextPlayer() {
        if (!this.humanPlayer.isFinished()) {
            JOptionPane.showMessageDialog(null, "Please finish your turn!");
            return;
        }
        this.addAdj = (this.addAdj + 1) % this.players.size();
        this.addMouseListener = (Player)this.players.get(this.addAdj);
        int n = this.random.nextInt(5) + 1;
        this.checkAccusation.setPlayer(this.addMouseListener, n);
        this.calcTargets(this.getCell(this.addMouseListener.getRow(), this.addMouseListener.getColumn()), n, this.addMouseListener.getMayStay());
        this.addMouseListener.makeMove();
        this.repaint();
    }

    public final void boardRepaint() {
        this.repaint();
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

    public final ArrayList<Card> getCards() {
        return this.cards;
    }

    public final Card getCard(String string) {
        for (Card d : this.cards) {
            if (!d.getCardName().equals(string)) continue;
            return d;
        }
        return null;
    }

    public final ArrayList<Player> getPlayers() {
        return this.players;
    }

    public final Player getPlayer(int n) {
        return this.players.get(n);
    }

    public final int getNumPlayers() {
        return this.players.size();
    }

    public final Solution getSolution() {
        return this.solution;
    }

    public final HumanPlayer getHuman() {
        return this.humanPlayer;
    }

    public final void setSolution(Card person, Card room, Card weapon) {
        this.solution.person = person;
        this.solution.room = room;
        this.solution.weapon = weapon;
    }

    public final void setPlayers(ArrayList players) {
        this.players = players;
    }

    public final void setPanels(ClueGame clueGame, GameControlPanel a, KnownCardsPanel k) {
        this.currGame = clueGame;
        this.checkAccusation = a;
        this.clearCards = k;
    }

    public void highlightTargets(boolean b) {
        if (this.targets != null) {
            for (BoardCell c : this.targets) {
                if (c.isRoom()) {
                    int n = 0;
                    while (n < this.rows) {
                        int n2 = 0;
                        while (n2 < this.cols) {
                            if (this.grid[n][n2].getRoom() == c.getRoom()) {
                                this.grid[n][n2].setHighlight(b);
                            }
                            ++n2;
                        }
                        ++n;
                    }
                    continue;
                }
                c.setHighlight(b);
            }
        }
    }
}
