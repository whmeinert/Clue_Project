package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
    private JButton add;
    private JButton addActionListener;
    private JTextField getBkColor;
    private JTextField getName;
    private JTextField nextPlayer;
    private JTextField orange;
    private Board repaint;

    public GameControlPanel(Board z) {
        this.repaint = z;
        this.setLayout(new GridLayout(2, 0));
        this.add(this.add());
        this.add(this.getBkColor());
    }

    private JPanel add() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 4));
        jPanel.add(this.addActionListener());
        jPanel.add(this.getRoll());
        this.add = new JButton("Make Accusation");
        jPanel.add(this.add);
        this.addActionListener = new JButton("NEXT!");
        jPanel.add(this.addActionListener);
        return jPanel;
    }

    private JPanel addActionListener() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Whose turn?"));
        this.orange = new JTextField(15);
        this.orange.setEditable(false);
        jPanel.add(this.orange);
        return jPanel;
    }

    private JPanel getBkColor() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 2));
        jPanel.add(this.getGuess());
        jPanel.add(this.getResult());
        return jPanel;
    }

    private JPanel getRoll() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Roll:"));
        this.getBkColor = new JTextField(5);
        this.getBkColor.setEditable(false);
        jPanel.add(this.getBkColor);
        return jPanel;
    }

    private JPanel getGuess() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 0));
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
        this.getName = new JTextField(20);
        this.getName.setEditable(false);
        jPanel.add(this.getName);
        return jPanel;
    }

    private JPanel getResult() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 0));
        jPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
        this.nextPlayer = new JTextField(20);
        this.nextPlayer.setEditable(false);
        jPanel.add(this.nextPlayer);
        return jPanel;
    }

    public final void setTurn(Player m, int n) {
        this.orange.setText(m.getName());
        this.orange.setBackground(m.getColor());
        this.getBkColor.setText(String.valueOf(n));
    }

    public final void setGuess(String string, Color color) {
        this.getName.setBackground(color);
        this.getName.setText(string);
    }

    public final void setGuessResult(String string, Color color) {
        this.nextPlayer.setBackground(color);
        this.nextPlayer.setText(string);
    }

    public static void main(String[] stringArray) {
        GameControlPanel a = new GameControlPanel(null);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(a);
        jFrame.setSize(750, 180);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        //a.setTurn(new ComputerPlayer("Col. Mustard", 0, 0, "orange"), 5);
        a.setGuess("I have no guess!", Color.orange);
        a.setGuessResult("So you have nothing?", null);
    }
}
