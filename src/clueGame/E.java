package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class E
        implements ActionListener {
    final /* synthetic */ G getCard;

    E(G g) {
        this.getCard = g;
    }

    @Override
    public final void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == G.I(this.getCard)) {
            G.I(this.getCard, true);
            G.I(this.getCard, new Solution());
            G.Z((G)this.getCard).person = G.C(this.getCard).getCard(G.B(this.getCard).getSelectedItem().toString());
            G.Z((G)this.getCard).weapon = G.C(this.getCard).getCard(G.D(this.getCard).getSelectedItem().toString());
            G.Z((G)this.getCard).room = G.C(this.getCard).getCard(G.F(this.getCard).getSelectedItem().toString());
        } else {
            G.I(this.getCard, false);
        }
        this.getCard.setVisible(false);
    }
}