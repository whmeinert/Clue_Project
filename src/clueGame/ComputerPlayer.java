package clueGame;

public class ComputerPlayer extends Player{
    private boolean getTargets = true;

    public ComputerPlayer(String string, int n, int n2, String string2) {
        super(string, n, n2, string2);
    }

    @Override
    public final void makeMove() {
        if (this.F.getTargets().size() == 0) {
            this.getTargets = true;
            return;
        }
        this.getTargets = false;
    }

    public final void finishTurn(BoardCell c) {
        this.getTargets = true;
        this.setLoc(c, false);
    }

    public final boolean isFinished() {
        return this.getTargets;
    }

    public final void setFinished(boolean bl) {
        this.getTargets = bl;
    }
}
