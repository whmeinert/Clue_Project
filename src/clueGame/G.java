package clueGame;

import clueGame.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class G
        extends JDialog {
    private JComboBox PERSON;
    private JComboBox ROOM;
    private JComboBox WEAPON;
    private String addActionListener;
    private JButton addItem;
    private JButton getCardName;
    private boolean getCardType;
    private Solution getCards;
    private Board getName;
    private ArrayList hasNext;

    public G(Board z, Room n) {
        this.getName = z;
        this.hasNext = z.getCards();
        this.setSize(350, 250);
        this.setLayout(new GridLayout(4, 2));
        this.setModal(true);
        this.WEAPON = new JComboBox();
        this.PERSON = new JComboBox();
        this.ROOM = new JComboBox();
        if (n == null) {
            this.setTitle("Make an Accusation");
            this.PERSON(this.WEAPON, CardType.ROOM, " Room");
        } else {
            this.setTitle("Make a Suggestion");
            this.addActionListener = n.getName();
            JLabel jLabel = new JLabel(" Current room");
            this.add(jLabel);
            JTextField jTextField = new JTextField(this.addActionListener);
            jTextField.setEditable(false);
            this.add(jTextField);
            this.WEAPON.addItem(this.addActionListener);
        }
        this.PERSON(this.PERSON, CardType.PERSON, " Person");
        this.PERSON(this.ROOM, CardType.WEAPON, " Weapon");
        this.ROOM();
        this.pack();
        this.setLocationRelativeTo(z);
    }

    private void PERSON(JComboBox jComboBox, CardType b, String string) {
        for (Object object2 : this.hasNext) {
            if (((Card)object2).getCardType() != b) continue;
            jComboBox.addItem(((Card)object2).getCardName());
        }
        Object object2 = new JLabel(string);
        this.add((Component)object2);
        this.add(jComboBox);
    }

    private void ROOM() {
        this.addItem = new JButton("Submit");
        this.add(this.addItem);
        this.getCardName = new JButton("Cancel");
        this.add(this.getCardName);
        E e = new E(this);
        this.addItem.addActionListener(e);
        this.getCardName.addActionListener(e);
    }

    public final boolean isSubmitted() {
        return this.getCardType;
    }

    public final Solution getSolution() {
        return this.getCards;
    }

    static final /* synthetic */ JButton I(G g) {
        return g.addItem;
    }

    static final /* synthetic */ void I(G g, boolean bl) {
        g.getCardType = bl;
    }

    static final /* synthetic */ void I(G g, Solution o) {
        g.getCards = o;
    }

    static final /* synthetic */ Solution Z(G g) {
        return g.getCards;
    }

    static final /* synthetic */ Board C(G g) {
        return g.getName;
    }

    static final /* synthetic */ JComboBox B(G g) {
        return g.PERSON;
    }

    static final /* synthetic */ JComboBox D(G g) {
        return g.ROOM;
    }

    static final /* synthetic */ JComboBox F(G g) {
        return g.WEAPON;
    }
}