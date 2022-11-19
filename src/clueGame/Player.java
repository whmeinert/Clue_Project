package clueGame;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.Timer;

public abstract class Player {
    private final String name;
    protected int row;
    protected int col;
    protected Board board;
    private Color color;
    private Color backColor;
    private ArrayList<Card> cards;
    protected Random random;
    protected Set<Card> seenCards;
    protected boolean mayStay;
    private boolean getCell;
    private int getColumn;
    private BoardCell getInstance;
    private BoardCell getPlayer;
    private Timer getRow;
    protected int fillOval;

    public Player(String name, int row, int col, String colorStr) {
        this.name = name;
        this.row = row;
        this.col = col;
        if (colorStr.contentEquals("red")) {
            this.color = new Color(255, 0, 0);
            this.backColor = new Color(253, 133, 133);
        } else if (colorStr.contentEquals("orange")) {
            this.color = new Color(225, 173, 1);
            this.backColor = new Color(252, 223, 109);
        } else if (colorStr.contentEquals("green")) {
            this.color = new Color(1, 213, 72);
            this.backColor = new Color(104, 252, 154);
        } else if (colorStr.contentEquals("blue")) {
            this.color = new Color(0, 150, 246);
            this.backColor = new Color(153, 214, 255);
        } else if (colorStr.contentEquals("white")) {
            this.color = new Color(242, 242, 242);
            this.backColor = new Color(255, 255, 255);
        } else if (colorStr.contentEquals("purple")) {
            this.color = new Color(253, 0, 204);
            this.backColor = new Color(236, 179, 255);
        }
        this.cards = new ArrayList<>();
        this.board = clueGame.Board.getInstance();
        this.seenCards = new HashSet();
        this.random = Board.getInstance().random;
    }

    public final void drawPlayer(Graphics2D graphics2D, int n, int n2, int n3) {
        int n4;
        int n5;
        if (this.getCell && this.getColumn > 0) {
            float f = (float)this.getColumn / 50.0f;
            n5 = (int)((float)n2 + (float)n * ((float)this.getInstance.getColumn() + f * (float)(this.getPlayer.getColumn() - this.getInstance.getColumn())));
            n4 = (int)((float)n3 + (float)n * ((float)this.getInstance.getRow() + f * (float)(this.getPlayer.getRow() - this.getInstance.getRow())));
        } else {
            int n6 = this.row;
            int n7 = this.col;
            if (this.getCell) {
                n6 = this.getInstance.getRow();
                n7 = this.getInstance.getColumn();
            }
            n5 = n7 * n + n2;
            n4 = n6 * n + n3;
            if (this.fillOval > 0) {
                int n8 = 0;
                while (n8 < this.fillOval) {
                    if (this.board.getPlayer(n8).getRow() == n6 && this.board.getPlayer(n8).getColumn() == n7) {
                        n5 += n / 2;
                    }
                    ++n8;
                }
            }
        }
        graphics2D.setColor(this.color);
        graphics2D.fillOval(n5, n4, n - 1, n - 1);
        graphics2D.setColor(Color.black);
        graphics2D.drawOval(n5, n4, n - 1, n - 1);
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

    public final void setLoc(BoardCell cell, boolean mayStay) {
        this.mayStay = false;
        if (cell != this.board.getCell(this.row, this.col)) {
            this.board.getCell(this.row, this.col).setOccupied(false);
            this.setupAnimateMove(cell, mayStay);
            this.row = cell.getRow();
            this.col = cell.getColumn();
            cell.setOccupied(true);
        }
    }

    public final boolean animateMove() {
        ++this.getColumn;
        return this.getColumn > 50;
    }

    public final void setupAnimateMove(BoardCell c, boolean bl) {
        this.getCell = true;
        this.getColumn = bl ? -50 : 0;
        this.getPlayer = c;
        this.getInstance = this.board.getCell(this.row, this.col);
        this.getRow = new Timer(10, new L(this));
        this.getRow.start();
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
    public final Color getBackColor() {
        return this.backColor;
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

    public final int getRow() {
        return this.row;
    }

    public final int getColumn() {
        return this.col;
    }

    public abstract void makeMove();

    public final void setMayStay(boolean bl) {
        this.mayStay = bl;
    }

    public final boolean getMayStay() {
        return this.mayStay;
    }

    static final /* synthetic */ void I(Player m, boolean bl) {
        m.getCell = bl;
    }

    static final /* synthetic */ Timer I(Player m) {
        return m.getRow;
    }
}
