package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AnimateMove
        implements ActionListener {
    final /* synthetic */ Player animateMove;

    AnimateMove(Player m) {
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

