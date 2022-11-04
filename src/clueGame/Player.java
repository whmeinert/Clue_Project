package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
    private String add;
    protected int Z;
    protected int C;
    private Color black;
    private Color contentEquals;
    private ArrayList drawOval;
    protected Random B;
    protected Set D;
    protected Board F;
    private static int equals = 0;
    protected int fillOval;
    protected boolean get;
    private boolean getCell;
    private int getColumn;
    private BoardCell getInstance;
    private BoardCell getPlayer;
    private Timer getRow;

    public Player(String string, int n, int n2, String string2) {
        this.add = string;
        this.Z = n;
        this.C = n2;
        this.F = clueGame.Board.getInstance();
        this.fillOval = equals++;
        this.drawOval = new ArrayList();
        this.D = new HashSet();
        if (string2.contentEquals("red")) {
            this.black = new Color(255, 0, 0);
            this.contentEquals = new Color(255, 128, 128);
        } else if (string2.contentEquals("orange")) {
            this.black = new Color(255, 204, 0);
            this.contentEquals = new Color(255, 224, 102);
        } else if (string2.contentEquals("green")) {
            this.black = new Color(0, 204, 68);
            this.contentEquals = new Color(102, 255, 153);
        } else if (string2.contentEquals("blue")) {
            this.black = new Color(0, 153, 255);
            this.contentEquals = new Color(153, 214, 255);
        } else if (string2.contentEquals("white")) {
            this.black = new Color(242, 242, 242);
            this.contentEquals = new Color(255, 255, 255);
        } else if (string2.contentEquals("purple")) {
            this.black = new Color(253, 0, 204);
            this.contentEquals = new Color(236, 179, 255);
        }
        this.B = clueGame.Board.getInstance().startLoc;
        this.get = false;
    }

    public final Card disproveSuggestion(Solution o) {
        ArrayList<Card> arrayList = new ArrayList<Card>();
        for (Object d2 : this.drawOval) {
            if (!d2.equals(o.person) && !d2.equals(o.weapon) && !d2.equals(o.room)) continue;
            arrayList.add((Card) d2);
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
        this.drawOval.add(d);
        this.D.add(d);
    }

    public final void setLoc(BoardCell c, boolean bl) {
        this.get = false;
        if (c != this.F.getCell(this.Z, this.C)) {
            this.F.getCell(this.Z, this.C).setOccupied(false);
            this.Z = c.getRow();
            this.C = c.getColumn();
            c.setOccupied(true);
        }
    }

    public final boolean animateMove() {
        ++this.getColumn;
        return this.getColumn > 50;
    }

    public abstract void makeMove();

    public final String getName() {
        return this.add;
    }

    public final int getRow() {
        return this.Z;
    }

    public final int getColumn() {
        return this.C;
    }

    public final Color getColor() {
        return this.black;
    }

    public final Color getBkColor() {
        return this.contentEquals;
    }

    public final ArrayList getHand() {
        return this.drawOval;
    }

    public final void clearCards() {
        this.drawOval = new ArrayList();
    }

    public final Set getSeen() {
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
