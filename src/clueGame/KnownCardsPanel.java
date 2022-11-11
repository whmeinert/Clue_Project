package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

public class KnownCardsPanel extends JPanel {
    private final JPanel PERSON;
    private final JPanel ROOM;
    private final JPanel WEAPON;
    private Board board;

    public KnownCardsPanel(Board board) {
        this.board = board;
        this.setLayout(new GridLayout(3, 1));
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(0), "Known Cards");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(titledBorder);

        // Create panels for People, Room, and Weapon
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

        // Add panels for People, Room, and Weapon
        this.add(this.PERSON);
        this.add(this.ROOM);
        this.add(this.WEAPON);
    }

    public final void updatePanels() {
        this.updatePanel(this.PERSON, CardType.PERSON);
        this.updatePanel(this.ROOM, CardType.ROOM);
        this.updatePanel(this.WEAPON, CardType.WEAPON);
        this.revalidate();
    }

    public final void updatePanel(JPanel jPanel, CardType cardType) {
        boolean hasCard = false;
        boolean seenCard = false;

        jPanel.removeAll();  // Reset all panels

        // Create panels for cards in human players hand
        JLabel jLabel = new JLabel("In Hand:");
        jPanel.add(jLabel);

        // Loop over all cards in human players hand
        for (Card card : this.board.getHuman().getHand()) {
            // Check that the card type is correct
            if (card.getCardType() != cardType) continue;
            JTextField jTextField = new JTextField(" " + card.getCardName() + " ", 12);
            jTextField.setBackground(this.board.getHuman().getBackColor());
            jTextField.setEditable(false);
            jPanel.add(jTextField);
            hasCard = true;  // Update that there is a card in the hand
        }

        // If hasCard is false have panel display none
        if (!hasCard) {
            JTextComponent textComponent;
            textComponent = new JTextField(" None ", 12);
            textComponent.setBackground(Color.white);
            textComponent.setEditable(false);
            jPanel.add(textComponent);
        }

        // Create panels for cards human players have seen
        jLabel = new JLabel("Seen:");
        jPanel.add(jLabel);
        int n = 1;

        // Loop for every player
        while (n < this.board.getNumPlayers()) {
            // Loop over every seen card
            for (Card card : this.board.getHuman().getSeen()) {
                // Check that card is correct type or not in the current players hand
                if (card.getCardType() != cardType || card.getHoldingPlayer() != this.board.getPlayer(n)) continue;
                JTextField jTextField = new JTextField(" " + card.getCardName() + " ");
                jTextField.setBackground(this.board.getPlayer(n).getBackColor());
                jTextField.setEditable(false);
                jPanel.add(jTextField);
                seenCard = true;  // Update that a card has been seen
            }
            n++;
        }

        // If seenCard is false have panel display none
        if (!seenCard) {
            JTextComponent textComponent;
            textComponent = new JTextField(" None ");
            textComponent.setEditable(false);
            jPanel.add(textComponent);
        }
    }

    public static void main(String[] stringArray) {
        Board board = Board.getInstance();
        board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
        board.initialize();

        // Add all cards to human players seen list
        for (Player player : board.getPlayers()){
            for (Card card : player.getHand()){
                board.getHuman().seenCards.add(card);
            }
        }

        KnownCardsPanel knownPanel = new KnownCardsPanel(board);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(knownPanel);
        jFrame.setSize(180, 700);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
