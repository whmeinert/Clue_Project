package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonListener
        implements ActionListener {
    final /* synthetic */ GameControlPanel getSource;

    private ButtonListener(GameControlPanel a) {
        this.getSource = a;
    }

    @Override
    public final void actionPerformed(ActionEvent actionEvent) {
        this.getSource.nextPlayer();
    }

    /* synthetic */ ButtonListener(GameControlPanel a, ButtonListener s) {
        this(a);
    }
}
