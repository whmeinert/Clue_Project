package clueGame;

public class Room {
	private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;
	
	public Room(String name) {
		this.name = name;
	}

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
	
	
	
}
