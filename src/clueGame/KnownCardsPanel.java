package clueGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

public class KnownCardsPanel extends JPanel {
    //private final JPanel PERSON;
    //private final JPanel ROOM;
    //private final JPanel WEAPON;
    private Board board;

    public KnownCardsPanel(Board z) {
        this.board = z;
        this.setLayout(new GridLayout(3, 1));
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(0), "Known Cards");
        titledBorder.setTitleJustification(2);
        this.setBorder(titledBorder);
        /*
        this.PERSON = new JPanel();
        this.PERSON.setLayout(new GridLayout(0, 1));
        this.PERSON.setBorder(new TitledBorder(new EtchedBorder(), "People"));
        this.ROOM = new JPanel();
        this.ROOM.setLayout(new GridLayout(0, 1));
        this.ROOM.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
        this.WEAPON = new JPanel();
        this.WEAPON.setLayout(new GridLayout(0, 1));
        this.WEAPON.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
        this.updatePanels();
        this.add(this.PERSON);
        this.add(this.ROOM);
        this.add(this.WEAPON);
        */
    }

    public final void updatePanels() {
        //this.updatePanel(this.PERSON, CardType.PERSON);
        //this.updatePanel(this.ROOM, CardType.ROOM);
        //this.updatePanel(this.WEAPON, CardType.WEAPON);
        this.revalidate();
    }

    public final void updatePanel(JPanel jPanel, CardType b) {
        boolean bl = false;
        boolean bl2 = false;
        jPanel.removeAll();
        JLabel jLabel = new JLabel("In Hand:");
        jPanel.add(jLabel);
        for (Card card : this.board.getHuman().getHand()) {
            if (card.getCardType() != b) continue;
            JTextField jTextField = new JTextField(" " + card.getCardName() + " ", 12);
            jTextField.setBackground(this.board.getHuman().getBackColor());
            jTextField.setEditable(false);
            jPanel.add(jTextField);
            bl = true;
        }
        if (!bl) {
            JTextComponent textComponent;
            textComponent = new JTextField(" None ", 12);
            textComponent.setBackground(Color.white);
            textComponent.setEditable(false);
            jPanel.add(textComponent);
        }
        jLabel = new JLabel("Seen:");
        jPanel.add(jLabel);
        int n = 1;
        while (n < this.board.getNumPlayers()) {
            for (Card card : this.board.getHuman().getSeen()) {
                if (card.getCardType() != b || card.getHoldingPlayer() != this.board.getPlayer(n)) continue;
                JTextField jTextField = new JTextField(" " + card.getCardName() + " ");
                jTextField.setBackground(this.board.getPlayer(n).getBackColor());
                jTextField.setEditable(false);
                jPanel.add(jTextField);
                bl2 = true;
            }
            ++n;
        }
        if (!bl2) {
            JTextComponent textComponent;
            textComponent = new JTextField(" None ");
            textComponent.setEditable(false);
            jPanel.add(textComponent);
        }
    }

    public static void main(String[] stringArray) {
        Board z = Board.getInstance();
        z.setConfigFiles("data/ClueLayout306.csv", "data/ClueSetup306.txt");
        z.initialize();
        for (Player player : z.getPlayers()){
            for (Card card : player.getHand()){
                z.getHuman().seenCards.add(card);
            }
        }
        KnownCardsPanel k = new KnownCardsPanel(z);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(k);
        jFrame.setSize(180, 700);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
