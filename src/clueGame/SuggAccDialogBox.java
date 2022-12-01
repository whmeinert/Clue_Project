package clueGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class SuggAccDialogBox extends JDialog {
    private final JComboBox PERSON;
    private final JComboBox ROOM;
    private final JComboBox WEAPON;
    private String cardName;
    private JButton submitButton;
    private JButton cancelButton;
    private boolean isEntered;
    private Solution solution;
    private Board board;
    private ArrayList cardList;

    public SuggAccDialogBox(Board board, Room room) {
        this.board = board;
        this.cardList = board.getCards();
        this.setSize(350, 250);
        this.setLayout(new GridLayout(4, 2));
        this.setModal(true);

        // Create combo boxes for each card types
        this.WEAPON = new JComboBox();
        this.PERSON = new JComboBox();
        this.ROOM = new JComboBox();

        // Check if the user is in a room or not and do a suggestion or an accusation based on that
        if (room == null) {
            this.setTitle("Make an Accusation");
            this.addComboBoxes(this.WEAPON, CardType.ROOM, " Room");
        } else {
            this.setTitle("Make a Suggestion");
            this.cardName = room.getName();
            JLabel jLabel = new JLabel(" Current room");
            this.add(jLabel);
            JTextField jTextField = new JTextField(this.cardName);
            jTextField.setEditable(false);
            this.add(jTextField);
            this.WEAPON.addItem(this.cardName);
        }
        this.addComboBoxes(this.PERSON, CardType.PERSON, " Person");
        this.addComboBoxes(this.ROOM, CardType.WEAPON, " Weapon");
        this.addButtons();
        this.pack();
        this.setLocationRelativeTo(board);
    }

    private void addComboBoxes(JComboBox jComboBox, CardType cardType, String string) {
        // Shuffle card list to prevent solution being first option
        Collections.shuffle(this.cardList);

        // iterate through each card and add it to the combo box
        for (Object object2 : this.cardList) {
            if (((Card)object2).getCardType() != cardType) continue;
            jComboBox.addItem(((Card)object2).getCardName());
        }
        Object object2 = new JLabel(string);
        this.add((Component)object2);
        this.add(jComboBox);
    }

    private void addButtons() {
        // Add the submit and cancel button to the box
        this.submitButton = new JButton("Submit");
        this.add(this.submitButton);
        this.cancelButton = new JButton("Cancel");
        this.add(this.cancelButton);
        AccButtonListener accButtonListener = new AccButtonListener(this);
        this.submitButton.addActionListener(accButtonListener);
        this.cancelButton.addActionListener(accButtonListener);
    }

    public final boolean isSubmitted() {
        return this.isEntered;
    }

    public final Solution getSolution() {
        return this.solution;
    }

    static final /* synthetic */ JButton I(SuggAccDialogBox dialogBox) {
        return dialogBox.submitButton;
    }

    static final /* synthetic */ void I(SuggAccDialogBox dialogBox, boolean bl) {
        dialogBox.isEntered = bl;
    }

    static final /* synthetic */ void I(SuggAccDialogBox dialogBox, Solution solution) {
        dialogBox.solution = solution;
    }

    static final /* synthetic */ Solution Z(SuggAccDialogBox dialogBox) {
        return dialogBox.solution;
    }

    static final /* synthetic */ Board C(SuggAccDialogBox dialogBox) {
        return dialogBox.board;
    }

    static final /* synthetic */ JComboBox B(SuggAccDialogBox dialogBox) {
        return dialogBox.PERSON;
    }

    static final /* synthetic */ JComboBox D(SuggAccDialogBox dialogBox) {
        return dialogBox.ROOM;
    }

    static final /* synthetic */ JComboBox F(SuggAccDialogBox dialogBox) {
        return dialogBox.WEAPON;
    }
}