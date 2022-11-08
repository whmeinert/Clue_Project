package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
    private final String name;
    protected int row;
    protected int col;
    protected Board board;
    private final String color;
    private ArrayList<Card> cards;
    protected Random B;
    protected Set<Card> D;
    protected boolean get;

    public Player(String name, int row, int col, String color) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.color = color;
        this.cards = new ArrayList<>();
        this.board = clueGame.Board.getInstance();
        this.D = new HashSet();
        this.B = Board.getInstance().random;
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
            int n = this.B.nextInt(arrayList.size());
            d3 = (Card)arrayList.get(n);
        }
        return d3;
    }

    public final void setLoc(BoardCell c, boolean bl) {
        this.get = false;
        if (c != this.board.getCell(this.row, this.col)) {
            this.board.getCell(this.row, this.col).setOccupied(false);
            this.row = c.getRow();
            this.col = c.getColumn();
            c.setOccupied(true);
        }
    }

    public final void updateSeen(Card d) {
        this.D.add(d);
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
        return this.D;
    }
}
