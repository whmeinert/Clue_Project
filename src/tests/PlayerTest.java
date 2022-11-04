package tests;

import java.util.Objects;
import java.util.Set;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class PlayerTest {
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
    public void cardsTest(){

    }

    @Test
    public void playerAndCardTest(){
        // Test that the expected number of players were loaded in
        assertEquals(6,board.getNumPlayers());

        // Test that the expected number of cards were loaded in
        assertEquals(21, board.getCards().size());

        // Check that all cards are either a person, weapon, or room
        for (int i = 0; i < board.getCards().size(); i++) {
            Card currCard = (Card) board.getCards().get(i);
            assertTrue(currCard.getCardType() == CardType.PERSON || currCard.getCardType() == CardType.ROOM || currCard.getCardType() == CardType.WEAPON);
        }

        // Check that the first player is the human and the rest are computers
        for (int i = 0; i < board.getNumPlayers(); i++) {
            if (i == 0) {
                assertEquals(board.getHuman(), board.getPlayer(i));
                assertTrue(Objects.equals(board.getPlayer(i).getName(), "Professor Plum"));
            }
            else {
                assertNotEquals(board.getHuman(), board.getPlayer(i));
            }
        }

        // Check that the answer was generated
        assertNotNull(board.getSolution());



    }

}
