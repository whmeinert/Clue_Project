package tests;

import clueGame.Board;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SuggestionTest {
    private static Board board;

    @BeforeAll
    public static void setUp() {
        // Board is singleton, get the only instance
        board = Board.getInstance();
        // set the file names to use my config files
        board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
        // Initialize will load config files
        board.initialize();
    }

    @Test
    public void SuggestionTest(){

    }

}
