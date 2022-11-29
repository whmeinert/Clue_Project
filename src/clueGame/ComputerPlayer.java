package clueGame;

import javax.swing.*;
import java.util.*;

public class ComputerPlayer extends Player{
    private boolean hasMoved = false;
    private Solution solution = new Solution();
    public ComputerPlayer(String name, int row, int col, String color) {
        super(name, row, col, color);
    }

    public final BoardCell selectTarget(Set<BoardCell> targets) {
        ArrayList<BoardCell> arrayList = new ArrayList<>();
        if (targets.size() == 0) {
            return this.board.getCell(this.row, this.col);
        }
        int n = 1001;
        for (BoardCell cell : targets) {
            this.checkCell(cell);
            n = Math.min(n, cell.getScore());
        }
        for (BoardCell cell : targets) {
            if (cell.getScore() != n) continue;
            arrayList.add(cell);
        }
        int n2 = this.random.nextInt(arrayList.size());
        return arrayList.get(n2);
    }

    private void checkCell(BoardCell cell) {
        int n = 1000;
        if (cell.isRoom()) {
            if (this.seenCards.contains(cell.getRoom().getCard())) {
                cell.setScore(50);
            } else {
                cell.setScore(0);
            }
            return;
        }
        int n2 = 0;
        while (n2 < this.board.getNumRows()) {
            int n3 = 0;
            while (n3 < this.board.getNumColumns()) {
                BoardCell cell2 = this.board.getCell(n2, n3);
                if (cell2.isDoorway()) {
                    int n4 = Math.abs(n2 - cell.getRow()) + Math.abs(n3 - cell.getColumn());
                    if (this.seenCards.contains(cell2.getToRoom().getCard())) {
                        n4 += 50;
                    }
                    n = Math.min(n, n4);
                }
                ++n3;
            }
            ++n2;
        }
        cell.setScore(n);
    }

    public final void makeSuggestion(BoardCell c) {
        Room n = c.getRoom();
        this.solution = this.createSuggestion(n);
        if (!this.board.doSuggestion(this.solution, this, n.getCenterCell()) && this.dontHaveRoomCard(n.getCard())) {
            this.hasMoved = true;
        }
    }

    public final boolean dontHaveRoomCard(Card d) {
        for (Card d2 : this.seenCards) {
            if (!d2.equals(d)) continue;
            return false;
        }
        return true;
    }

    public final Solution createSuggestion(Room room) {
        Solution newSuggestion = new Solution();
        newSuggestion.room = room.getCard();
        ArrayList<Card> personList = new ArrayList<Card>();
        ArrayList<Card> weaponList = new ArrayList<Card>();
        for (Card card : this.board.getCards()) {
            if (card.getCardType() == clueGame.CardType.PERSON && !this.seenCards.contains(card)) {
                personList.add(card);
                continue;
            }
            if (card.getCardType() != clueGame.CardType.WEAPON || this.seenCards.contains(card)) { continue; }
            weaponList.add(card);
        }
        int randIndex = this.random.nextInt(personList.size());
        newSuggestion.person = (Card)personList.get(randIndex);
        randIndex = this.random.nextInt(weaponList.size());
        newSuggestion.weapon = (Card)weaponList.get(randIndex);
        return newSuggestion;
    }

    @Override
    public final void makeMove() {
        if (this.hasMoved) {
            this.makeAccusation();
        } else {
            Set targets = this.board.getTargets();
            BoardCell cell = this.selectTarget(targets);
            this.setLoc(cell, false);
            if (cell.isRoom()) {
                this.makeSuggestion(cell);
            }
        }
    }

    public final void makeAccusation() {
        String string = String.valueOf(this.solution.person.getCardName()) + ", " + this.solution.room.getCardName() + ", " + this.solution.weapon.getCardName();
        boolean bl = this.board.checkAccusation(this.solution);
        if (bl) {
            JOptionPane.showMessageDialog(null, "The computer just won, answer is " + string);
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "The computer made an incorrect guess of " + string);
        }
    }

}
