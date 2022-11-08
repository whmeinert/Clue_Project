package clueGame;

import java.util.*;

public class ComputerPlayer extends Player{
    public ComputerPlayer(String name, int row, int col, String color) {
        super(name, row, col, color);
    }

    public final BoardCell selectTarget(Set<BoardCell> set) {
        ArrayList<BoardCell> arrayList = new ArrayList<>();
        if (set.size() == 0) {
            return this.board.getCell(this.row, this.col);
        }
        int n = 1001;
        for (BoardCell c : set) {
            this.I(c);
            n = Math.min(n, c.getScore());
        }
        for (BoardCell c : set) {
            if (c.getScore() != n) continue;
            arrayList.add(c);
        }
        int n2 = this.B.nextInt(arrayList.size());
        return arrayList.get(n2);
    }

    private void I(BoardCell c) {
        int n = 1000;
        if (c.isRoom()) {
            if (this.D.contains(c.getRoom().getCard())) {
                c.setScore(50);
            } else {
                c.setScore(0);
            }
            return;
        }
        int n2 = 0;
        while (n2 < this.board.getNumRows()) {
            int n3 = 0;
            while (n3 < this.board.getNumColumns()) {
                BoardCell c2 = this.board.getCell(n2, n3);
                if (c2.isDoorway()) {
                    int n4 = Math.abs(n2 - c.getRow()) + Math.abs(n3 - c.getColumn());
                    if (this.D.contains(c2.getToRoom().getCard())) {
                        n4 += 50;
                    }
                    n = Math.min(n, n4);
                }
                ++n3;
            }
            ++n2;
        }
        c.setScore(n);
    }

    public final Solution createSuggestion(Room n) {
        Solution newSuggestion = new Solution();
        newSuggestion.room = n.getCard();
        ArrayList<Card> arrayList = new ArrayList<Card>();
        ArrayList<Card> arrayList2 = new ArrayList<Card>();
        for (Card d : this.board.getCards()) {
            if (d.getCardType() == clueGame.CardType.PERSON && !this.D.contains(d)) {
                arrayList.add(d);
                continue;
            }
            if (d.getCardType() != clueGame.CardType.WEAPON || this.D.contains(d)) { continue; }
            arrayList2.add(d);
        }
        int n2 = this.B.nextInt(arrayList.size());
        newSuggestion.person = (Card)arrayList.get(n2);
        n2 = this.B.nextInt(arrayList2.size());
        newSuggestion.weapon = (Card)arrayList2.get(n2);
        return newSuggestion;
    }
}
