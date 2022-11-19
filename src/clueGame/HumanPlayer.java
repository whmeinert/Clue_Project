package clueGame;

import javax.swing.*;

public class HumanPlayer extends Player{
    private boolean finished = true;
    public HumanPlayer(String name, int row, int col, String color) {
        super(name, row, col, color);
    }

    @Override
    public final void makeMove() {
        if (this.board.getTargets().size() == 0) {
            JOptionPane.showMessageDialog(null, "No move is available.");
            this.finished = true;
            return;
        }
        this.finished = false;
        this.board.highlightTargets(true);
    }

    public final void finishTurn(BoardCell c) {
        this.finished = true;
        this.setLoc(c, false);
    }

    public boolean isFinished() {
        return this.finished;
    }

    public final void setFinished(boolean bl) {
        this.finished = bl;
    }

}
