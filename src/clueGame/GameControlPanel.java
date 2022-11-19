package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
    private JButton accButton;
    private JButton nextButton;
    private JTextField rollField;
    private JTextField guessField;
    private JTextField guessResultField;
    private JTextField currPlayerField;
    private Board board;

    public GameControlPanel(Board board) {
        this.board = board;
        this.setLayout(new GridLayout(2, 0));
        this.add(this.getUpperPanels());
        this.add(this.getLowerPanels());
    }

    public final void nextPlayer() {
        this.setGuess("", null);
        this.setGuessResult("", null);
        this.board.nextPlayer();
        this.repaint();
    }

    // Creates and sets the panels in the upper half of the main panel
    private JPanel getUpperPanels() {
        JPanel jPanel = new JPanel();
        ButtonListener s = new ButtonListener(this, null);
        jPanel.setLayout(new GridLayout(1, 4));
        jPanel.add(this.addActionListener());
        jPanel.add(this.getRoll());
        this.accButton = new JButton("Make Accusation");
        this.accButton.addActionListener(s);
        jPanel.add(this.accButton);
        this.nextButton = new JButton("NEXT!");
        this.nextButton.addActionListener(s);
        jPanel.add(this.nextButton);
        return jPanel;
    }

    private JPanel addActionListener() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Whose turn?"));
        this.currPlayerField = new JTextField(15);
        this.currPlayerField.setEditable(false);
        jPanel.add(this.currPlayerField);
        return jPanel;
    }

    // Creates and sets the panels in the lower half of the main panel
    private JPanel getLowerPanels() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 2));
        jPanel.add(this.getGuess());
        jPanel.add(this.getResult());
        return jPanel;
    }

    // Creates the panel that displays the current roll
    private JPanel getRoll() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Roll:"));
        this.rollField = new JTextField(5);
        this.rollField.setEditable(false);
        jPanel.add(this.rollField);
        return jPanel;
    }

    // Creates the panel that displays the guess
    private JPanel getGuess() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 0));
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
        this.guessField = new JTextField(20);
        this.guessField.setEditable(false);
        jPanel.add(this.guessField);
        return jPanel;
    }

    // Creates the panel that displays the guess result
    private JPanel getResult() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 0));
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
        this.guessResultField = new JTextField(20);
        this.guessResultField.setEditable(false);
        jPanel.add(this.guessResultField);
        return jPanel;
    }

    // Sets the text in the field to be the current player and their associated color
    public final void setPlayer(Player player, int n) {
        this.currPlayerField.setText(player.getName());
        this.currPlayerField.setBackground(player.getBackColor());
        this.rollField.setText(String.valueOf(n));
    }

    // Sets the text in the field to be the current guess and the players associated color
    public final void setGuess(String string, Color color) {
        this.guessField.setBackground(color);
        this.guessField.setText(string);
    }

    // Sets the text in the field to be the current guess result
    public final void setGuessResult(String string, Color color) {
        this.guessResultField.setBackground(color);
        this.guessResultField.setText(string);
    }

    public static void main(String[] stringArray) {
        GameControlPanel a = new GameControlPanel(null);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(a);
        jFrame.setSize(750, 180);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        // Test panel setters and getters
        a.setPlayer(new ComputerPlayer("Professor Plum", 0, 0, "purple"), 5);
        a.setGuess("I have no guess!", new  Color(236, 179, 255));
        a.setGuessResult("So you have nothing?", null);
    }

    public void setTurn(Player addMouseListener, int n) {
    }

    static final /* synthetic */ JButton I(GameControlPanel a) {
        return a.nextButton;
    }

    static final /* synthetic */ JButton Z(GameControlPanel a) {
        return a.accButton;
    }

    static final /* synthetic */ Board C(GameControlPanel a) {
        return a.board;
    }
}
