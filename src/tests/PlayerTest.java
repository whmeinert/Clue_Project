package tests;

import java.util.Objects;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
            Card testCard = (Card) board.getCards().get(i);
            assertTrue(testCard.getCardType() == CardType.PERSON || testCard.getCardType() == CardType.ROOM || testCard.getCardType() == CardType.WEAPON);
        }


        for (int i = 0; i < board.getNumPlayers(); i++) {
            Player testPlayer = board.getPlayer(i);

            // Check that the player is instantiated
            assertNotNull(testPlayer.getName());
            assertNotNull(testPlayer.getHand());

            // Check that the first player is the human and the rest are computers
            if (i == 0) {
                assertEquals(board.getHuman(), testPlayer);
                assertTrue(Objects.equals(testPlayer.getName(), "Professor Plum"));
            }
            else {
                assertNotEquals(board.getHuman(), testPlayer);
            }



            // check that solution is not dealt to players
            for (int j = 0; j < testPlayer.getHand().size(); j++) {
                assertFalse(testPlayer.getHand().get(j) == board.getSolution().person);
                assertFalse(testPlayer.getHand().get(j) == board.getSolution().room);
                assertFalse(testPlayer.getHand().get(j) == board.getSolution().weapon);
            }
        }

        // Check that the answer was dealt
        assertNotNull(board.getSolution().person);
        assertNotNull(board.getSolution().room);
        assertNotNull(board.getSolution().weapon);



    }

}
