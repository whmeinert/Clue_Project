/*
 * Decompiled with CFR 0.151.
 */
package clueGame;

import clueGame.GameControlPanel;
import clueGame.KnownCardsPanel;
import clueGame.Board;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame
        extends JFrame {
    private String append = "data/ClueLayout.csv";
    private String getHuman = "data/ClueSetup.txt";
    private Board getInstance;
    private GameControlPanel getName;
    private KnownCardsPanel initialize;

    public ClueGame() {
        this.append();
    }

    public ClueGame(String string, String string2) {
        this.append = string;
        this.getHuman = string2;
        this.append();
    }

    private void append() {
        this.setTitle("CSCI306 Clue Game");
        this.getInstance = Board.getInstance();
        this.getInstance.setConfigFiles(this.append, this.getHuman);
        try {
            this.getInstance.initialize();
        }
        catch (Exception exception) {
            System.out.println("error!");
        }
        this.getName = new GameControlPanel(this.getInstance);
        this.initialize = new KnownCardsPanel(this.getInstance);
        this.setDefaultCloseOperation(3);
        this.add((Component)this.getInstance, "Center");
        this.add((Component)this.getName, "South");
        this.add((Component)this.initialize, "East");
        this.getInstance.setPanels(this, this.getName, this.initialize);
        this.setSize(820, 880);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        String string = this.getInstance.getHuman().getName();
        JOptionPane.showMessageDialog(this, "    You are " + string + ".\n  Can you find the solution\nbefore the Computer players?", "Welcome to Clue", 1);
    }

    public static final void main(String[] stringArray) {
        ClueGame clueGame = new ClueGame();
    }
}

