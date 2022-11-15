/*
 * Decompiled with CFR 0.151.
 */
package clueGame;

import java.awt.Component;
import javax.swing.*;

public class ClueGame
        extends JFrame {
    private String layoutConfigFile = "data/ClueLayout306.csv";
    private String setupConfigFile = "data/ClueSetup306.txt";
    private Board board;
    private GameControlPanel gameControlPanel;
    private KnownCardsPanel knownCardsPanel;

    public ClueGame() {
        this.append();
    }

    public ClueGame(String string, String string2) {
        this.layoutConfigFile = string;
        this.setupConfigFile = string2;
        this.append();
    }

    private void append() {
        this.setTitle("CSCI306 Clue Game");
        this.board = Board.getInstance();
        this.board.setConfigFiles(this.layoutConfigFile, this.setupConfigFile);
        try {
            this.board.initialize();
        }
        catch (Exception exception) {
            System.out.println("error!");
        }
        this.gameControlPanel = new GameControlPanel(this.board);
        this.knownCardsPanel = new KnownCardsPanel(this.board);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add((Component)this.board, "Center");
        this.add((Component)this.gameControlPanel, "South");
        this.add((Component)this.knownCardsPanel, "East");
        this.board.setPanels(this, this.gameControlPanel, this.knownCardsPanel);
        this.setSize(820, 880);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        String string = this.board.getHuman().getName();
        JOptionPane.showMessageDialog(this, "    You are " + string + ".\n  Can you find the solution\nbefore the Computer players?", "Welcome to Clue", 1);
    }

    public static final void main(String[] stringArray) {
        ClueGame clueGame = new ClueGame();
    }
}

