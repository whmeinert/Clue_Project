package clueGame;

import java.awt.*;

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

	public final void drawLabel(Graphics2D graphics2D, int n, int n2, int n3) {
		if (this.labelCell == null) {
			return;
		}
		int n4 = n * 2 / 3;
		int n5 = n * this.labelCell.getColumn() + n2;
		int n6 = n * (1 + this.labelCell.getRow()) + n3;
		Font font = new Font("Comic Sans MS", 1, n4);
		graphics2D.setFont(font);
		graphics2D.setColor(Color.BLUE);
		graphics2D.drawString(this.name, n5, n6);
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
