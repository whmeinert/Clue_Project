package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
    private final String name;
    protected int row;
    protected int col;
    private final String color;
    private ArrayList<Card> cards;

    public Player(String name, int row, int col, String color) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.color = color;
        this.cards = new ArrayList<>();
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
