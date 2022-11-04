package clueGame;

public class Room {
	private final String name;
    private BoardCell centerCell;
    private BoardCell labelCell;
	private Card card;
	
    // initialize room object
	public Room(String name, Card card) {
		this.name = name;
		this.card = card;
	}
	

	// getters and setters
	
	public Object getName() {
		return this.name;
	}

	public BoardCell getLabelCell() {
		return this.labelCell;
	}

	public BoardCell getCenterCell() {
		return this.centerCell;
	}

	public void setCenterCell(BoardCell boardCell) {
		this.centerCell = boardCell;
		
	}

	public void setLabelCell(BoardCell boardCell) {
		this.labelCell = boardCell;
		
	}

	public final Card getCard() {
		return this.card;
	}
	
}
