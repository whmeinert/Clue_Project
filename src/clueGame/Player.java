package clueGame;

import java.util.*;

public abstract class Player {
    private final String name;
    protected int row;
    protected int col;
    protected Board board;
    private final String color;
    private ArrayList<Card> cards;
    protected Random random;
    protected Set<Card> seenCards;

    public Player(String name, int row, int col, String color) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.color = color;
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

    public final String getColor() {
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
