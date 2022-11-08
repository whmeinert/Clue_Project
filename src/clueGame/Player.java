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
    protected Set D;
    private ArrayList<Card> drawOval;

    public Player(String name, int row, int col, String color) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.color = color;
        this.cards = new ArrayList<>();
        this.board = clueGame.Board.getInstance();
        this.drawOval = new ArrayList();
    }

    public final Card disproveSuggestion(Solution o) {
        Card d3;
        ArrayList<Card> arrayList = new ArrayList<Card>();
        for (Card d2 : this.drawOval) {
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

    public final void addToHand(Card d) {
        this.cards.add(d);
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
}
