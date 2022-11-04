package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
    private String name;
    protected int row;
    protected int col;
    private Color color;
    private Color contentEquals;
    private ArrayList<Card> cards;
    protected Random B;
    protected Set<Card> D;
    protected Board F;
    private static int equals = 0;
    protected int fillOval;
    protected boolean get;
    private boolean getCell;
    private int getColumn;
    private BoardCell getInstance;
    private BoardCell getPlayer;
    private Timer getRow;

    public Player(String name, int row, int col, String color) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.F = clueGame.Board.getInstance();
        this.fillOval = equals++;
        this.cards = new ArrayList<>();
        this.D = new HashSet<Card>();
        if (color.contentEquals("red")) {
            this.color = new Color(255, 0, 0);
            this.contentEquals = new Color(255, 128, 128);
        } else if (color.contentEquals("orange")) {
            this.color = new Color(255, 204, 0);
            this.contentEquals = new Color(255, 224, 102);
        } else if (color.contentEquals("green")) {
            this.color = new Color(0, 204, 68);
            this.contentEquals = new Color(102, 255, 153);
        } else if (color.contentEquals("blue")) {
            this.color = new Color(0, 153, 255);
            this.contentEquals = new Color(153, 214, 255);
        } else if (color.contentEquals("white")) {
            this.color = new Color(242, 242, 242);
            this.contentEquals = new Color(255, 255, 255);
        } else if (color.contentEquals("purple")) {
            this.color = new Color(253, 0, 204);
            this.contentEquals = new Color(236, 179, 255);
        }
        this.B = clueGame.Board.getInstance().startLoc;
        this.get = false;
    }

    public final Card disproveSuggestion(Solution o) {
        ArrayList<Card> arrayList = new ArrayList<Card>();
        for (Card d2 : this.cards) {
            if (!d2.equals(o.person) && !d2.equals(o.weapon) && !d2.equals(o.room)) continue;
            arrayList.add(d2);
        }
        Card d2 = null;
        if (arrayList.size() > 0) {
            int n = this.B.nextInt(arrayList.size());
            d2 = (Card)arrayList.get(n);
        }
        return d2;
    }

    public final void updateSeen(Card d) {
        this.D.add(d);
    }

    public final void addToHand(Card d) {
        this.cards.add(d);
        this.D.add(d);
    }

    public final void setLoc(BoardCell c, boolean bl) {
        this.get = false;
        if (c != this.F.getCell(this.row, this.col)) {
            this.F.getCell(this.row, this.col).setOccupied(false);
            this.row = c.getRow();
            this.col = c.getColumn();
            c.setOccupied(true);
        }
    }

    public final boolean animateMove() {
        ++this.getColumn;
        return this.getColumn > 50;
    }

    public abstract void makeMove();

    public final String getName() {
        return this.name;
    }

    public final int getRow() {
        return this.row;
    }

    public final int getColumn() {
        return this.col;
    }

    public final Color getColor() {
        return this.color;
    }

    public final Color getBkColor() {
        return this.contentEquals;
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

    public final void setMayStay(boolean bl) {
        this.get = bl;
    }

    public final boolean getMayStay() {
        return this.get;
    }

    static final /* synthetic */ void I(Player m, boolean bl) {
        m.getCell = bl;
    }

    static final /* synthetic */ Timer I(Player m) {
        return m.getRow;
    }
}
