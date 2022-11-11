package clueGame;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Player {
    private final String name;
    protected int row;
    protected int col;
    protected Board board;
    private Color color;
    private Color contentEquals;
    private ArrayList<Card> cards;
    protected Random random;
    protected Set<Card> seenCards;

    protected boolean get;

    public Player(String name, int row, int col, String colorStr) {
        this.name = name;
        this.row = row;
        this.col = col;
        if (colorStr.contentEquals("red")) {
            this.color = new Color(255, 0, 0);
            this.contentEquals = new Color(255, 128, 128);
        } else if (colorStr.contentEquals("orange")) {
            this.color = new Color(255, 196, 0);
            this.contentEquals = new Color(255, 221, 56, 255);
        } else if (colorStr.contentEquals("green")) {
            this.color = new Color(0, 255, 0);
            this.contentEquals = new Color(106, 255, 156);
        } else if (colorStr.contentEquals("blue")) {
            this.color = new Color(0, 153, 255);
            this.contentEquals = new Color(153, 214, 255);
        } else if (colorStr.contentEquals("white")) {
            this.color = new Color(232, 232, 232);
            this.contentEquals = new Color(255, 255, 255);
        } else if (colorStr.contentEquals("purple")) {
            this.color = new Color(219, 0, 253);
            this.contentEquals = new Color(230, 179, 255);
        }
        this.cards = new ArrayList<>();
        this.board = clueGame.Board.getInstance();
        this.seenCards = new HashSet();
        this.random = Board.getInstance().random;
    }

    public final Card disproveSuggestion(Solution o) {
        Card d3;
        ArrayList<Card> arrayList = new ArrayList<>();
        for (Card d2 : this.cards) {
            if (!d2.equals(o.person) && !d2.equals(o.weapon) && !d2.equals(o.room)) continue;
            arrayList.add(d2);
        }
        d3 = null;
        if (arrayList.size() > 0) {
            int n = this.random.nextInt(arrayList.size());
            d3 = (Card)arrayList.get(n);
        }
        return d3;
    }

    public final void setLoc(BoardCell cell) {
        if (cell != this.board.getCell(this.row, this.col)) {
            this.board.getCell(this.row, this.col).setOccupied(false);
            this.row = cell.getRow();
            this.col = cell.getColumn();
            cell.setOccupied(true);
        }
    }

    public final void updateSeen(Card d) {
        this.seenCards.add(d);
    }

    public final void addToHand(Card d) {
        this.cards.add(d);
        updateSeen(d);
    }

    public final String getName() {
        return this.name;
    }

    public final Color getColor() {
        return this.color;
    }

    public final ArrayList<Card> getHand() {
        return this.cards;
    }

    public final void clearCards() {
        this.cards = new ArrayList<>();
    }

    public final Set<Card> getSeen() {
        return this.seenCards;
    }
}
