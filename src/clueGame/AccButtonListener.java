package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AccButtonListener
        implements ActionListener {
    final /* synthetic */ SuggAccDialogBox dialogBox;

    AccButtonListener(SuggAccDialogBox dialogBox) {
        this.dialogBox = dialogBox;
    }

    @Override
    public final void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == SuggAccDialogBox.I(this.dialogBox)) {
            SuggAccDialogBox.I(this.dialogBox, true);
            SuggAccDialogBox.I(this.dialogBox, new Solution());
            SuggAccDialogBox.Z((SuggAccDialogBox)this.dialogBox).person = SuggAccDialogBox.C(this.dialogBox).getCard(SuggAccDialogBox.B(this.dialogBox).getSelectedItem().toString());
            SuggAccDialogBox.Z((SuggAccDialogBox)this.dialogBox).weapon = SuggAccDialogBox.C(this.dialogBox).getCard(SuggAccDialogBox.D(this.dialogBox).getSelectedItem().toString());
            SuggAccDialogBox.Z((SuggAccDialogBox)this.dialogBox).room = SuggAccDialogBox.C(this.dialogBox).getCard(SuggAccDialogBox.F(this.dialogBox).getSelectedItem().toString());
        } else {
            SuggAccDialogBox.I(this.dialogBox, false);
        }
        this.dialogBox.setVisible(false);
    }
}