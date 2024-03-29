package tests;

import java.util.ArrayList;
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

public class PlayerCardTest {
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
    public void playerTest(){
        // Test that the expected number of players were loaded in
        assertEquals(6,board.getNumPlayers());

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

    @Test
    public void cardTest(){
        // Test that the expected number of cards were loaded in
        assertEquals(21, board.getCards().size());

        // Check that all cards are either a person, weapon, or room
        for (int i = 0; i < board.getCards().size(); i++) {
            Card testCard = (Card) board.getCards().get(i);
            assertTrue(testCard.getCardType() == CardType.PERSON || testCard.getCardType() == CardType.ROOM || testCard.getCardType() == CardType.WEAPON);
        }

        // Check that no card is dealt twice
        ArrayList<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < board.getNumPlayers(); i++) {
            Player testPlayer = board.getPlayer(i);
            dealtCards.addAll(testPlayer.getHand());
        }
        dealtCards.add(board.getSolution().person);
        dealtCards.add(board.getSolution().room);
        dealtCards.add(board.getSolution().weapon);
        for (int i = 1; i < dealtCards.size(); i++) {
            for (int j = i; j < dealtCards.size(); j++) {
                assertFalse(dealtCards.get(i-1) == dealtCards.get(j));
            }
        }
    }

}
