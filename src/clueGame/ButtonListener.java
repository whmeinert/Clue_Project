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
        if (actionEvent.getSource() == GameControlPanel.I(this.getSource)) {
            this.getSource.nextPlayer();
        } else if (actionEvent.getSource() == GameControlPanel.Z(this.getSource)) {
            //GameControlPanel.C(this.getSource).makeAccusation();
        }
    }

    /* synthetic */ ButtonListener(GameControlPanel a, ButtonListener s) {
        this(a);
    }
}
