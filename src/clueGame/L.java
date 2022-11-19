package clueGame;

import clueGame.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class L
        implements ActionListener {
    final /* synthetic */ Player animateMove;

    L(Player m) {
        this.animateMove = m;
    }

    @Override
    public final void actionPerformed(ActionEvent actionEvent) {
        if (this.animateMove.animateMove()) {
            Player.I(this.animateMove, false);
            Player.I(this.animateMove).stop();
        }
        this.animateMove.board.boardRepaint();
    }
}

