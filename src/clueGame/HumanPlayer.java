package clueGame;

import javax.swing.*;

public class HumanPlayer extends Player{
    private boolean mayStay = true;
    public HumanPlayer(String name, int row, int col, String color) {
        super(name, row, col, color);
    }

    @Override
    public final void makeMove() {
        if (this.board.getTargets().size() == 0) {
            JOptionPane.showMessageDialog(null, "No move is available.");
            this.mayStay = true;
            return;
        }
        this.mayStay = false;
        this.board.highlightTargets(true);
    }

    public boolean isFinished() {
        return this.mayStay;
    }


}
