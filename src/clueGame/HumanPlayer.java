package clueGame;

public class HumanPlayer extends Player{
    private boolean mayStay = true;
    public HumanPlayer(String name, int row, int col, String color) {
        super(name, row, col, color);
    }

    public boolean isFinished() {
        return this.mayStay;
    }


}
